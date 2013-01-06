package okj.easy.core;

import com.badlogic.gdx.utils.Disposable;

/**
 * 
 * @FileName: ResourceContext.java
 * @CreateOn: Sep 15, 2012 - 11:11:32 AM
 * @Author: TrungNT
 */
public interface ResourceContext extends Disposable
{
    /**
     * When this mode enable , the references of data will only be store not
     * load
     */
    public void setWaitMode (boolean isRefStore);

    public boolean isWaitMode ();

    /**
     * Reload all resource after isTottlayUnloaded or when unload data size > 0
     * Make sure no untrack data
     */
    public void reload ();

    /**
     * context still keep track to the data but the data was unloaded from
     * manager and waiting for reload
     */
    public void unload ();

    /**
     * when all data is unloaded
     * 
     * @return is tottlay unloaded all data
     */
    public boolean isTotallyUnloaded ();

    /**
     * when all data is loaded
     * 
     * @return is tottlay loaded all data
     */
    public boolean isTotallyLoaded ();

    /**
     * @return the size of data this context manage
     */
    public int size ();

    /**
     * call the coordinate asset manager update to load all data
     */
    public boolean update ();

    /**
     * totally remove this context
     */
    public void dispose ();
}
