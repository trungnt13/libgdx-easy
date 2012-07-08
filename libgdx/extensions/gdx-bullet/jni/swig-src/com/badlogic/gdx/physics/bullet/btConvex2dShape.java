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

public class btConvex2dShape extends btConvexShape {
  private long swigCPtr;

  protected btConvex2dShape(long cPtr, boolean cMemoryOwn) {
    super(gdxBulletJNI.btConvex2dShape_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  public static long getCPtr(btConvex2dShape obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        gdxBulletJNI.delete_btConvex2dShape(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public btConvex2dShape(btConvexShape convexChildShape) {
    this(gdxBulletJNI.new_btConvex2dShape(btConvexShape.getCPtr(convexChildShape), convexChildShape), true);
  }

  public btConvexShape getChildShape() {
    long cPtr = gdxBulletJNI.btConvex2dShape_getChildShape__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new btConvexShape(cPtr, false);
  }

}
