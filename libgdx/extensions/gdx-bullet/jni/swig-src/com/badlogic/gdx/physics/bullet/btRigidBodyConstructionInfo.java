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

public class btRigidBodyConstructionInfo {
  private long swigCPtr;
  protected boolean swigCMemOwn;

  protected btRigidBodyConstructionInfo(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  public static long getCPtr(btRigidBodyConstructionInfo obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        gdxBulletJNI.delete_btRigidBodyConstructionInfo(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setM_mass(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_mass_set(swigCPtr, this, value);
  }

  public float getM_mass() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_mass_get(swigCPtr, this);
  }

  public void setM_motionState(btMotionState value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_motionState_set(swigCPtr, this, btMotionState.getCPtr(value), value);
  }

  public btMotionState getM_motionState() {
    long cPtr = gdxBulletJNI.btRigidBodyConstructionInfo_m_motionState_get(swigCPtr, this);
    return (cPtr == 0) ? null : btMotionState.newDerivedObject(cPtr, false);
  }

  public void setM_startWorldTransform(btTransform value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_startWorldTransform_set(swigCPtr, this, btTransform.getCPtr(value), value);
  }

  public btTransform getM_startWorldTransform() {
    long cPtr = gdxBulletJNI.btRigidBodyConstructionInfo_m_startWorldTransform_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btTransform(cPtr, false);
  }

  public void setM_collisionShape(btCollisionShape value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_collisionShape_set(swigCPtr, this, btCollisionShape.getCPtr(value), value);
  }

  public btCollisionShape getM_collisionShape() {
    long cPtr = gdxBulletJNI.btRigidBodyConstructionInfo_m_collisionShape_get(swigCPtr, this);
    return (cPtr == 0) ? null : btCollisionShape.newDerivedObject(cPtr, false);
  }

  public void setM_localInertia(btVector3 value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_localInertia_set(swigCPtr, this, btVector3.getCPtr(value), value);
  }

  public btVector3 getM_localInertia() {
    long cPtr = gdxBulletJNI.btRigidBodyConstructionInfo_m_localInertia_get(swigCPtr, this);
    return (cPtr == 0) ? null : new btVector3(cPtr, false);
  }

  public void setM_linearDamping(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_linearDamping_set(swigCPtr, this, value);
  }

  public float getM_linearDamping() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_linearDamping_get(swigCPtr, this);
  }

  public void setM_angularDamping(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_angularDamping_set(swigCPtr, this, value);
  }

  public float getM_angularDamping() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_angularDamping_get(swigCPtr, this);
  }

  public void setM_friction(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_friction_set(swigCPtr, this, value);
  }

  public float getM_friction() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_friction_get(swigCPtr, this);
  }

  public void setM_restitution(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_restitution_set(swigCPtr, this, value);
  }

  public float getM_restitution() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_restitution_get(swigCPtr, this);
  }

  public void setM_linearSleepingThreshold(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_linearSleepingThreshold_set(swigCPtr, this, value);
  }

  public float getM_linearSleepingThreshold() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_linearSleepingThreshold_get(swigCPtr, this);
  }

  public void setM_angularSleepingThreshold(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_angularSleepingThreshold_set(swigCPtr, this, value);
  }

  public float getM_angularSleepingThreshold() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_angularSleepingThreshold_get(swigCPtr, this);
  }

  public void setM_additionalDamping(boolean value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalDamping_set(swigCPtr, this, value);
  }

  public boolean getM_additionalDamping() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalDamping_get(swigCPtr, this);
  }

  public void setM_additionalDampingFactor(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalDampingFactor_set(swigCPtr, this, value);
  }

  public float getM_additionalDampingFactor() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalDampingFactor_get(swigCPtr, this);
  }

  public void setM_additionalLinearDampingThresholdSqr(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalLinearDampingThresholdSqr_set(swigCPtr, this, value);
  }

  public float getM_additionalLinearDampingThresholdSqr() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalLinearDampingThresholdSqr_get(swigCPtr, this);
  }

  public void setM_additionalAngularDampingThresholdSqr(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalAngularDampingThresholdSqr_set(swigCPtr, this, value);
  }

  public float getM_additionalAngularDampingThresholdSqr() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalAngularDampingThresholdSqr_get(swigCPtr, this);
  }

  public void setM_additionalAngularDampingFactor(float value) {
    gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalAngularDampingFactor_set(swigCPtr, this, value);
  }

  public float getM_additionalAngularDampingFactor() {
    return gdxBulletJNI.btRigidBodyConstructionInfo_m_additionalAngularDampingFactor_get(swigCPtr, this);
  }

  public btRigidBodyConstructionInfo(float mass, btMotionState motionState, btCollisionShape collisionShape, Vector3 localInertia) {
    this(gdxBulletJNI.new_btRigidBodyConstructionInfo__SWIG_0(mass, btMotionState.getCPtr(motionState), motionState, btCollisionShape.getCPtr(collisionShape), collisionShape, localInertia), true);
  }

  public btRigidBodyConstructionInfo(float mass, btMotionState motionState, btCollisionShape collisionShape) {
    this(gdxBulletJNI.new_btRigidBodyConstructionInfo__SWIG_1(mass, btMotionState.getCPtr(motionState), motionState, btCollisionShape.getCPtr(collisionShape), collisionShape), true);
  }

}
