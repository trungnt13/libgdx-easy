package okj.easy.core;

import com.badlogic.gdx.utils.Array;

/**
 * 
 * @FileName: ResourcePack.java
 * @CreateOn: Sep 15, 2012 - 11:11:27 AM
 * @Author: TrungNT
 */
public class ResourcePack implements ResourceContext
{
	public final String name;

	private final Array<ResourceContext> mManageContext;

	public ResourcePack(String name, ResourceContext... contexts) {
		this.name = name;

		mManageContext = new Array<ResourceContext>();
		mManageContext.addAll(contexts);
	}

	public ResourcePack(String name) {
		this.name = name;

		mManageContext = new Array<ResourceContext>();
	}

	/*************************************************
	 * ResourceContext manage
	 *************************************************/

	public void manage (ResourceContext... contexts)
	{
		mManageContext.addAll(contexts);
	}

	public void unmanage (ResourceContext... contexts)
	{
		for (ResourceContext context : contexts)
			mManageContext.removeValue(context, true);
	}

	@Override
	public void reload ()
	{
		for (ResourceContext context : mManageContext)
			context.reload();
	}

	@Override
	public void unload ()
	{
		for (ResourceContext context : mManageContext)
			context.unload();
	}

	/*************************************************
	 * Query info
	 *************************************************/

	@Override
	public boolean isTotallyUnloaded ()
	{
		for (ResourceContext context : mManageContext)
			if (!context.isTotallyUnloaded())
				return false;
		return true;
	}

	@Override
	public boolean isTotallyLoaded ()
	{
		for (ResourceContext context : mManageContext)
			if (!context.isTotallyLoaded())
				return false;
		return true;
	}

	@Override
	public int size ()
	{
		return mManageContext.size;
	}

	/*************************************************
	 * Query info
	 *************************************************/

	@Override
	public boolean update ()
	{
		return eAdmin.econtext.update() & eAdmin.eaudio.update();
	}

	@Override
	public void clear ()
	{
		mManageContext.clear();
	}

	@Override
	public void dispose ()
	{
		for (ResourceContext context : mManageContext)
			context.dispose();
		mManageContext.clear();
	}

	public void setRefStoreMode (boolean isRefStore)
	{
		for (ResourceContext context : mManageContext) {
			context.setRefStoreMode(isRefStore);
		}
	}

	public boolean isRefStoreMode ()
	{
		return false;
	}

}
