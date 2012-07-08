/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 2.0.5
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.badlogic.gdx.physics.bullet;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Matrix3;

public class btIntIndexData {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected btIntIndexData(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(btIntIndexData obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        gdxBulletJNI.delete_btIntIndexData(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setM_value(int value) {
    gdxBulletJNI.btIntIndexData_m_value_set(swigCPtr, this, value);
  }

  public int getM_value() {
    return gdxBulletJNI.btIntIndexData_m_value_get(swigCPtr, this);
  }

  public btIntIndexData() {
    this(gdxBulletJNI.new_btIntIndexData(), true);
  }

}
