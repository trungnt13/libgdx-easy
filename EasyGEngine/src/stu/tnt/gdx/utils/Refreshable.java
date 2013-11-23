package stu.tnt.gdx.utils;


/**
 * This interface use for a view which manager it child and need to refresh to 
 * validate them position or size ...
 * @author trung
 *
 */
public interface Refreshable {
	/**
	 * Tell the parent view that all its child need to be invalidate and 
	 * refresh
	 */
	public void refresh();
}
