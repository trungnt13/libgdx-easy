package org.ege.utils;

public interface Factory<T>
{
    public T newObject ();

    public T newObject (Object... objects);
}
