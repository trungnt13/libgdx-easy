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

public class btEmptyAlgorithm extends btCollisionAlgorithm {
  private long swigCPtr;

  protected btEmptyAlgorithm(long cPtr, boolean cMemoryOwn) {
    super(gdxBulletJNI.btEmptyAlgorithm_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  public static long getCPtr(btEmptyAlgorithm obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        gdxBulletJNI.delete_btEmptyAlgorithm(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public btEmptyAlgorithm(btCollisionAlgorithmConstructionInfo ci) {
    this(gdxBulletJNI.new_btEmptyAlgorithm(btCollisionAlgorithmConstructionInfo.getCPtr(ci), ci), true);
  }

}
