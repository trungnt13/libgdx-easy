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

public class btHingeConstraint extends btTypedConstraint {
  private long swigCPtr;

  protected btHingeConstraint(long cPtr, boolean cMemoryOwn) {
    super(gdxBulletJNI.btHingeConstraint_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  public static long getCPtr(btHingeConstraint obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        gdxBulletJNI.delete_btHingeConstraint(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public btHingeConstraint(btRigidBody rbA, btRigidBody rbB, Vector3 pivotInA, Vector3 pivotInB, Vector3 axisInA, Vector3 axisInB, boolean useReferenceFrameA) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_0(btRigidBody.getCPtr(rbA), rbA, btRigidBody.getCPtr(rbB), rbB, pivotInA, pivotInB, axisInA, axisInB, useReferenceFrameA), true);
  }

  public btHingeConstraint(btRigidBody rbA, btRigidBody rbB, Vector3 pivotInA, Vector3 pivotInB, Vector3 axisInA, Vector3 axisInB) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_1(btRigidBody.getCPtr(rbA), rbA, btRigidBody.getCPtr(rbB), rbB, pivotInA, pivotInB, axisInA, axisInB), true);
  }

  public btHingeConstraint(btRigidBody rbA, Vector3 pivotInA, Vector3 axisInA, boolean useReferenceFrameA) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_2(btRigidBody.getCPtr(rbA), rbA, pivotInA, axisInA, useReferenceFrameA), true);
  }

  public btHingeConstraint(btRigidBody rbA, Vector3 pivotInA, Vector3 axisInA) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_3(btRigidBody.getCPtr(rbA), rbA, pivotInA, axisInA), true);
  }

  public btHingeConstraint(btRigidBody rbA, btRigidBody rbB, btTransform rbAFrame, btTransform rbBFrame, boolean useReferenceFrameA) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_4(btRigidBody.getCPtr(rbA), rbA, btRigidBody.getCPtr(rbB), rbB, btTransform.getCPtr(rbAFrame), rbAFrame, btTransform.getCPtr(rbBFrame), rbBFrame, useReferenceFrameA), true);
  }

  public btHingeConstraint(btRigidBody rbA, btRigidBody rbB, btTransform rbAFrame, btTransform rbBFrame) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_5(btRigidBody.getCPtr(rbA), rbA, btRigidBody.getCPtr(rbB), rbB, btTransform.getCPtr(rbAFrame), rbAFrame, btTransform.getCPtr(rbBFrame), rbBFrame), true);
  }

  public btHingeConstraint(btRigidBody rbA, btTransform rbAFrame, boolean useReferenceFrameA) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_6(btRigidBody.getCPtr(rbA), rbA, btTransform.getCPtr(rbAFrame), rbAFrame, useReferenceFrameA), true);
  }

  public btHingeConstraint(btRigidBody rbA, btTransform rbAFrame) {
    this(gdxBulletJNI.new_btHingeConstraint__SWIG_7(btRigidBody.getCPtr(rbA), rbA, btTransform.getCPtr(rbAFrame), rbAFrame), true);
  }

  public void getInfo1NonVirtual(SWIGTYPE_p_btTypedConstraint__btConstraintInfo1 info) {
    gdxBulletJNI.btHingeConstraint_getInfo1NonVirtual(swigCPtr, this, SWIGTYPE_p_btTypedConstraint__btConstraintInfo1.getCPtr(info));
  }

  public void getInfo2NonVirtual(btConstraintInfo2 info, btTransform transA, btTransform transB, Vector3 angVelA, Vector3 angVelB) {
    gdxBulletJNI.btHingeConstraint_getInfo2NonVirtual(swigCPtr, this, btConstraintInfo2.getCPtr(info), info, btTransform.getCPtr(transA), transA, btTransform.getCPtr(transB), transB, angVelA, angVelB);
  }

  public void getInfo2Internal(btConstraintInfo2 info, btTransform transA, btTransform transB, Vector3 angVelA, Vector3 angVelB) {
    gdxBulletJNI.btHingeConstraint_getInfo2Internal(swigCPtr, this, btConstraintInfo2.getCPtr(info), info, btTransform.getCPtr(transA), transA, btTransform.getCPtr(transB), transB, angVelA, angVelB);
  }

  public void getInfo2InternalUsingFrameOffset(btConstraintInfo2 info, btTransform transA, btTransform transB, Vector3 angVelA, Vector3 angVelB) {
    gdxBulletJNI.btHingeConstraint_getInfo2InternalUsingFrameOffset(swigCPtr, this, btConstraintInfo2.getCPtr(info), info, btTransform.getCPtr(transA), transA, btTransform.getCPtr(transB), transB, angVelA, angVelB);
  }

  public void updateRHS(float timeStep) {
    gdxBulletJNI.btHingeConstraint_updateRHS(swigCPtr, this, timeStep);
  }

  public btRigidBody getRigidBodyA() {
    return new btRigidBody(gdxBulletJNI.btHingeConstraint_getRigidBodyA__SWIG_0(swigCPtr, this), false);
  }

  public btRigidBody getRigidBodyB() {
    return new btRigidBody(gdxBulletJNI.btHingeConstraint_getRigidBodyB__SWIG_0(swigCPtr, this), false);
  }

  public btTransform getFrameOffsetA() {
    return new btTransform(gdxBulletJNI.btHingeConstraint_getFrameOffsetA(swigCPtr, this), false);
  }

  public btTransform getFrameOffsetB() {
    return new btTransform(gdxBulletJNI.btHingeConstraint_getFrameOffsetB(swigCPtr, this), false);
  }

  public void setFrames(btTransform frameA, btTransform frameB) {
    gdxBulletJNI.btHingeConstraint_setFrames(swigCPtr, this, btTransform.getCPtr(frameA), frameA, btTransform.getCPtr(frameB), frameB);
  }

  public void setAngularOnly(boolean angularOnly) {
    gdxBulletJNI.btHingeConstraint_setAngularOnly(swigCPtr, this, angularOnly);
  }

  public void enableAngularMotor(boolean enableMotor, float targetVelocity, float maxMotorImpulse) {
    gdxBulletJNI.btHingeConstraint_enableAngularMotor(swigCPtr, this, enableMotor, targetVelocity, maxMotorImpulse);
  }

  public void enableMotor(boolean enableMotor) {
    gdxBulletJNI.btHingeConstraint_enableMotor(swigCPtr, this, enableMotor);
  }

  public void setMaxMotorImpulse(float maxMotorImpulse) {
    gdxBulletJNI.btHingeConstraint_setMaxMotorImpulse(swigCPtr, this, maxMotorImpulse);
  }

  public void setMotorTarget(Quaternion qAinB, float dt) {
    gdxBulletJNI.btHingeConstraint_setMotorTarget__SWIG_0(swigCPtr, this, qAinB, dt);
  }

  public void setMotorTarget(float targetAngle, float dt) {
    gdxBulletJNI.btHingeConstraint_setMotorTarget__SWIG_1(swigCPtr, this, targetAngle, dt);
  }

  public void setLimit(float low, float high, float _softness, float _biasFactor, float _relaxationFactor) {
    gdxBulletJNI.btHingeConstraint_setLimit__SWIG_0(swigCPtr, this, low, high, _softness, _biasFactor, _relaxationFactor);
  }

  public void setLimit(float low, float high, float _softness, float _biasFactor) {
    gdxBulletJNI.btHingeConstraint_setLimit__SWIG_1(swigCPtr, this, low, high, _softness, _biasFactor);
  }

  public void setLimit(float low, float high, float _softness) {
    gdxBulletJNI.btHingeConstraint_setLimit__SWIG_2(swigCPtr, this, low, high, _softness);
  }

  public void setLimit(float low, float high) {
    gdxBulletJNI.btHingeConstraint_setLimit__SWIG_3(swigCPtr, this, low, high);
  }

  public void setAxis(Vector3 axisInA) {
    gdxBulletJNI.btHingeConstraint_setAxis(swigCPtr, this, axisInA);
  }

  public float getLowerLimit() {
    return gdxBulletJNI.btHingeConstraint_getLowerLimit(swigCPtr, this);
  }

  public float getUpperLimit() {
    return gdxBulletJNI.btHingeConstraint_getUpperLimit(swigCPtr, this);
  }

  public float getHingeAngle() {
    return gdxBulletJNI.btHingeConstraint_getHingeAngle__SWIG_0(swigCPtr, this);
  }

  public float getHingeAngle(btTransform transA, btTransform transB) {
    return gdxBulletJNI.btHingeConstraint_getHingeAngle__SWIG_1(swigCPtr, this, btTransform.getCPtr(transA), transA, btTransform.getCPtr(transB), transB);
  }

  public void testLimit(btTransform transA, btTransform transB) {
    gdxBulletJNI.btHingeConstraint_testLimit(swigCPtr, this, btTransform.getCPtr(transA), transA, btTransform.getCPtr(transB), transB);
  }

  public btTransform getAFrame() {
    return new btTransform(gdxBulletJNI.btHingeConstraint_getAFrame__SWIG_0(swigCPtr, this), false);
  }

  public btTransform getBFrame() {
    return new btTransform(gdxBulletJNI.btHingeConstraint_getBFrame__SWIG_0(swigCPtr, this), false);
  }

  public int getSolveLimit() {
    return gdxBulletJNI.btHingeConstraint_getSolveLimit(swigCPtr, this);
  }

  public float getLimitSign() {
    return gdxBulletJNI.btHingeConstraint_getLimitSign(swigCPtr, this);
  }

  public boolean getAngularOnly() {
    return gdxBulletJNI.btHingeConstraint_getAngularOnly(swigCPtr, this);
  }

  public boolean getEnableAngularMotor() {
    return gdxBulletJNI.btHingeConstraint_getEnableAngularMotor(swigCPtr, this);
  }

  public float getMotorTargetVelosity() {
    return gdxBulletJNI.btHingeConstraint_getMotorTargetVelosity(swigCPtr, this);
  }

  public float getMaxMotorImpulse() {
    return gdxBulletJNI.btHingeConstraint_getMaxMotorImpulse(swigCPtr, this);
  }

  public boolean getUseFrameOffset() {
    return gdxBulletJNI.btHingeConstraint_getUseFrameOffset(swigCPtr, this);
  }

  public void setUseFrameOffset(boolean frameOffsetOnOff) {
    gdxBulletJNI.btHingeConstraint_setUseFrameOffset(swigCPtr, this, frameOffsetOnOff);
  }

  public void setParam(int num, float value, int axis) {
    gdxBulletJNI.btHingeConstraint_setParam__SWIG_0(swigCPtr, this, num, value, axis);
  }

  public void setParam(int num, float value) {
    gdxBulletJNI.btHingeConstraint_setParam__SWIG_1(swigCPtr, this, num, value);
  }

  public float getParam(int num, int axis) {
    return gdxBulletJNI.btHingeConstraint_getParam__SWIG_0(swigCPtr, this, num, axis);
  }

  public float getParam(int num) {
    return gdxBulletJNI.btHingeConstraint_getParam__SWIG_1(swigCPtr, this, num);
  }

}
