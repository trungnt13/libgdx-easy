package stu.tnt.ai.kbs.data;

import com.badlogic.gdx.utils.Pool.Poolable;

public abstract class Valueable implements Poolable {
	private String mMessage = null;

	// ///////////////////////////////////////////////////////////////
	// value configuration
	// ///////////////////////////////////////////////////////////////

	public boolean isTrue() {
		return isTrueInternal() && isBinded();
	}

	protected abstract boolean isTrueInternal();

	// ///////////////////////////////////////////////////////////////
	// reset and re-useable methods
	// ///////////////////////////////////////////////////////////////

	public void reset() {
		mMessage = null;
		resetInternal();
	}

	protected abstract void resetInternal();

	// ///////////////////////////////////////////////////////////////
	// message manage
	// ///////////////////////////////////////////////////////////////
	public String getMessage() {
		return mMessage;
	}

	protected void setMessage(String mess) {
		if (!mess.contains(prefix()))
			throw new RuntimeException("The message must contain prefix: "
					+ prefix());
		mMessage = mess;
	}

	protected abstract String prefix();

	/**
	 * Check whether the message is setted and this Valueable take effect
	 */
	public boolean isBinded() {
		return mMessage == null;
	}

}
