package stu.tnt.ai.kbs.data;

import stu.tnt.Updateable;

public abstract class DataRaw<T> implements Updateable {
	protected boolean isRecording = true;

	// duration recording
	private float countDown = 0;
	private float recordingDuration = -1;

	// time recording
	private int recordingTime = 0;

	public final String Name;

	private RecordingFrequency mFrequency;

	protected T currValue;
	protected T lastValue;

	protected DataRaw(String name) {
		if (name.contains(prefix()))
			throw new RuntimeException("The Data name must contain prefix: "
					+ prefix());

		Name = name;
		currValue = defData();
		lastValue = defData();
	}

	// ///////////////////////////////////////////////////////////////
	// simple method
	// ///////////////////////////////////////////////////////////////
	public RecordingFrequency getRecordingFrequency() {
		return mFrequency;
	}

	/**
	 * 
	 */
	public void setRecording(boolean isRecording, RecordingFrequency freg,
			float param) {
		if (this.isRecording == isRecording)
			return;

		this.isRecording = isRecording;
		this.mFrequency = freg;

		if (freg == RecordingFrequency.Duration)
			this.recordingDuration = param;
		else if (freg == RecordingFrequency.NumberOfTime)
			this.recordingTime = (int) param;

		// start new record session
		if (isRecording) {
			currValue = defData();
			lastValue = defData();
			countDown = 0;
		}
	}

	public float getRecordingDuration() {
		return recordingDuration;
	}

	public float getRecordingTime() {
		return recordingTime;
	}

	private final void checkNumbOfTimeRecording() {
		if (mFrequency == RecordingFrequency.NumberOfTime) {
			countDown++;
			if (countDown >= recordingTime) {
				// reset
				lastValue = currValue;
				currValue = defData();
			}
		}
	}

	// ///////////////////////////////////////////////////////////////
	// recording methods
	// ///////////////////////////////////////////////////////////////
	public void copy(T raw) {
		if (!isRecording)
			return;

		checkNumbOfTimeRecording();
		copyInternal(raw);
	}

	public DataRaw<T> push(T raw) {
		if (!isRecording)
			return this;

		checkNumbOfTimeRecording();
		pushInternal(raw);
		return this;
	}

	// ///////////////////////////////////////////////////////////////
	// abstract methods
	// ///////////////////////////////////////////////////////////////

	public void copy(DataRaw<T> data) {
		copy(data.data());
	}

	public T data() {
		if (mFrequency == RecordingFrequency.NumberOfTime
				|| mFrequency == RecordingFrequency.Duration) {
			return lastValue;
		} else {
			return currValue;
		}
	}

	protected abstract void copyInternal(T raw);

	protected abstract void pushInternal(T raw);

	public abstract T defData();

	public abstract String prefix();

	// ///////////////////////////////////////////////////////////////
	// override methods
	// ///////////////////////////////////////////////////////////////

	@Override
	public boolean equals(Object arg0) {
		return data().equals(((DataRaw) arg0).data());
	}

	@Override
	public void update(float delta) {
		if (!isRecording || mFrequency == RecordingFrequency.InfiniteTime)
			return;

		// duration recording
		if (mFrequency == RecordingFrequency.Duration) {
			if (recordingDuration >= 0) {
				countDown += delta;
				if (countDown >= recordingDuration) {
					// reset
					lastValue = currValue;
					currValue = defData();
					countDown = 0;
				}
			}
		}
	}

	public static enum RecordingFrequency {
		NumberOfTime, InfiniteTime, Duration
	}
}
