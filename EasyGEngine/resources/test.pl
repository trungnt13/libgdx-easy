% Welcome to rules-swi.pl (~cs9414/public_html/notes/kr/rules/rules-swi.pl).
% To run the system, save a copy in your home directory, then (on a host that
% runs SWI Prolog), do:
%    % prolog -s rules.pl
%    ?- wm(X). % should reply that the only working memory facts are a, b, c.
%    ?- trace. % optional - this makes Prolog show the goals it's executing
%    ?- run.   % runs rule-based system. See note below.
%    ?- notrace. % turn off tracing
%    ?- wm(X). % this time it should say that a, b, c, d, & e are all facts.
%    ?- ^D
% Note: as there there are two rules (rule1 and rule2), and they are both
%       eligible to fire given that a, b, and c are all true, both rules should
%       fire, and the conclusion d of rule1 and the conclusion e of rule2
%       should both be facts when execution finishes.
%
% Original code by Claude Sammut, date lost in the mists of time.
% Modified for iProlog by Bill Wilson, October 2001.
% Modified for SWI Prolog by Bill Wilson, April 2006.

:- dynamic wm/1. % this allows inferred wm-facts to be asserted.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% To express rules in a natural way, we define some operators
% First argument to op is the precedence
% Second argument is the associativity (left, right, nonassoc)
% Third argument is operator name (or list of names)
% This is basically a cosmetic trick to avoid writing the rules as
% if(rule1, then(and(a, and(b, c)), d).
% if(rule2, then(and(a, b), e).
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

:- op(900, xfx ,if).
:- op(800, xfx, then).
:- op(700, xfy, and).

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Example Rules
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

rule1
if a and b and c then d.

rule2
if a and b then e.

%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Initial Working memory
% We use "wm" to distinguish working memory elements from
% other entries in Prolog's data base
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

wm(a).
wm(b).
wm(c).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Reset
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% 1. Assert a dummy already_fired(_,_) fact so retract will not fail if there
%    are no real such facts to retract.
% 2. Next retract all already_fired(_,_) assertions, in case we are rerunning
%    the system and there are some old assertions lying around from last time.
% 3. Now re-assert the dummy already_fired(_,_) fact so that "can_fire" will
%    be confused by the fact that there is no such predicate as already_fired
%    the first time around the match-resolve-act cycle.
% 4. Finally, the cut prevents this predicate endlessly cycling, asserting and
%    retracting.

init :-
        assert(already_fired(null, false)),
        retract(already_fired(_, _)),
        assert(already_fired(null, false)), !.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Start execution
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

run :-
	init,
        exec.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Select a rule, fire it and repeat until no rules are satisfied
% f "fire" succeeds, cut will prevent backtracking
% If "fire" fails, the cycle will repeat
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

exec :-
    repeat,                % Always succeeds on backtracking
    select_rule(R),        % Select a single rule for firing
    fire(R), !.


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% "findall" collects all solutions to "can_fire"
% resolve selects one of those rules
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

select_rule(SelectedRule) :-
    findall(Rule, can_fire(Rule), Candidates),
    resolve(Candidates, SelectedRule).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Find a rule that hasn't fired before and has its conditions satisfied
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

can_fire(RuleName if Condition then Conclusion) :-
    RuleName if Condition then Conclusion,    % Look up rule in data base
    not(already_fired(RuleName, Condition)),  % Has it already fired?
    satisfied(Condition).                     % Are all conditions satisfied?


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% If pattern is "A and ..." then look for A in working memory
% then check rest recursively.
%
%    (A and B) = (x and y and z)
%    A = x
%    B = y and Z
%
% If pattern is a single predicate. Look it up.
% Note that "!" prevents a conjunction reaching the second clause
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

satisfied(A and B) :- !,
    wm(A),
    satisfied(B).
satisfied(A) :-
    wm(A).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Very simple conflict resolution strategy: pick the first rule
% Also check in case no rules were found
%
% Exercise: rewrite this to choose the rule which has the most conditions
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

resolve([], []).
resolve([X|_], X).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% Add "already_fired" clause to data base so that this instance of the rule
%    is never fired again.
% Add all terms in conclusion to database, if not already there
% Fail to force backtracking so that a new execution cycle begins
%
% If there is no rule to fire, succeed. This terminates execution cycle
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

fire(RuleName if Condition then Conclusion) :- !,
    assert(already_fired(RuleName, Condition)),
    add_to_wm(Conclusion),
    fail.
fire(_).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% For each term in condition
%    add it to workking memory if it is not already there.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

add_to_wm(A and B) :- !,
    assert_if_not_present(A),
    add_to_wm(B).
add_to_wm(A) :-
    assert_if_not_present(A).


%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
% If term is in working memory, don't do anything
% Otherwise, add new term to working memory.
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

assert_if_not_present(A) :-
    wm(A), !.
assert_if_not_present(A) :-
    assert(wm(A)).