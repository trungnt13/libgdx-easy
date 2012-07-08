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

public class btSubSimplexClosestResult {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected btSubSimplexClosestResult(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(btSubSimplexClosestResult obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        gdxBulletJNI.delete_btSubSimplexClosestResult(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setM_closestPointOnSimplex(btVector3 value) {
    gdxBulletJNI.btSubSimplexClosestResult_m_closestPointOnSimplex_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getM_closestPointOnSimplex() {
    long cPtr = gdxBulletJNI.btSubSimplexClosestResult_m_closestPointOnSimplex_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setM_usedVertices(btUsageBitfield value) {
    gdxBulletJNI.btSubSimplexClosestResult_m_usedVertices_set(swigCPtr, this, btUsageBitfield.getCPtr(value), value);
  }

  public btUsageBitfield getM_usedVertices() {
    long cPtr = gdxBulletJNI.btSubSimplexClosestResult_m_usedVertices_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btUsageBitfield(cPtr, false);
  }

  public void setM_barycentricCoords(float[] value) {
    gdxBulletJNI.btSubSimplexClosestResult_m_barycentricCoords_set(swigCPtr, this, value);
  }

  public float[] getM_barycentricCoords() {
    return gdxBulletJNI.btSubSimplexClosestResult_m_barycentricCoords_get(swigCPtr, this);
  }

  public void setM_degenerate(boolean value) {
    gdxBulletJNI.btSubSimplexClosestResult_m_degenerate_set(swigCPtr, this, value);
  }

  public boolean getM_degenerate() {
    return gdxBulletJNI.btSubSimplexClosestResult_m_degenerate_get(swigCPtr, this);
  }

  public void reset() {
    gdxBulletJNI.btSubSimplexClosestResult_reset(swigCPtr, this);
  }

  public boolean isValid() {
    return gdxBulletJNI.btSubSimplexClosestResult_isValid(swigCPtr, this);
  }

  public void setBarycentricCoordinates(float a, float b, float c, float d) {
    gdxBulletJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_0(swigCPtr, this, a, b, c, d);
  }

  public void setBarycentricCoordinates(float a, float b, float c) {
    gdxBulletJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_1(swigCPtr, this, a, b, c);
  }

  public void setBarycentricCoordinates(float a, float b) {
    gdxBulletJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_2(swigCPtr, this, a, b);
  }

  public void setBarycentricCoordinates(float a) {
    gdxBulletJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_3(swigCPtr, this, a);
  }

  public void setBarycentricCoordinates() {
    gdxBulletJNI.btSubSimplexClosestResult_setBarycentricCoordinates__SWIG_4(swigCPtr, this);
  }

  public btSubSimplexClosestResult() {
    this(gdxBulletJNI.new_btSubSimplexClosestResult(), true);
  }

}
