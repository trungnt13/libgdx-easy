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

public class btDiscreteDynamicsWorld extends btDynamicsWorld {
  private long swigCPtr;

  protected btDiscreteDynamicsWorld(long cPtr, boolean cMemoryOwn) {
    super(gdxBulletJNI.btDiscreteDynamicsWorld_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  public static long getCPtr(btDiscreteDynamicsWorld obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        gdxBulletJNI.delete_btDiscreteDynamicsWorld(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public btDiscreteDynamicsWorld(btDispatcher dispatcher, btBroadphaseInterface pairCache, btConstraintSolver constraintSolver, btCollisionConfiguration collisionConfiguration) {
    this(gdxBulletJNI.new_btDiscreteDynamicsWorld(btDispatcher.getCPtr(dispatcher), dispatcher, btBroadphaseInterface.getCPtr(pairCache), pairCache, btConstraintSolver.getCPtr(constraintSolver), constraintSolver, btCollisionConfiguration.getCPtr(collisionConfiguration), collisionConfiguration), true);
  }

  public int stepSimulation(float timeStep, int maxSubSteps, float fixedTimeStep) {
    return gdxBulletJNI.btDiscreteDynamicsWorld_stepSimulation__SWIG_0(swigCPtr, this, timeStep, maxSubSteps, fixedTimeStep);
  }

  public int stepSimulation(float timeStep, int maxSubSteps) {
    return gdxBulletJNI.btDiscreteDynamicsWorld_stepSimulation__SWIG_1(swigCPtr, this, timeStep, maxSubSteps);
  }

  public int stepSimulation(float timeStep) {
    return gdxBulletJNI.btDiscreteDynamicsWorld_stepSimulation__SWIG_2(swigCPtr, this, timeStep);
  }

  public void synchronizeSingleMotionState(btRigidBody body) {
    gdxBulletJNI.btDiscreteDynamicsWorld_synchronizeSingleMotionState(swigCPtr, this, btRigidBody.getCPtr(body), body);
  }

  public void addConstraint(btTypedConstraint constraint, boolean disableCollisionsBetweenLinkedBodies) {
    gdxBulletJNI.btDiscreteDynamicsWorld_addConstraint__SWIG_0(swigCPtr, this, btTypedConstraint.getCPtr(constraint), constraint, disableCollisionsBetweenLinkedBodies);
  }

  public void addConstraint(btTypedConstraint constraint) {
    gdxBulletJNI.btDiscreteDynamicsWorld_addConstraint__SWIG_1(swigCPtr, this, btTypedConstraint.getCPtr(constraint), constraint);
  }

  public btSimulationIslandManager getSimulationIslandManager() {
    long cPtr = gdxBulletJNI.btDiscreteDynamicsWorld_getSimulationIslandManager__SWIG_0(swigCPtr, this);
    return (cPtr == 0) ? null : new btSimulationIslandManager(cPtr, false);
  }

  public btCollisionWorld getCollisionWorld() {
    long cPtr = gdxBulletJNI.btDiscreteDynamicsWorld_getCollisionWorld(swigCPtr, this);
    return (cPtr == 0) ? null : new btCollisionWorld(cPtr, false);
  }

  public void addCollisionObject(btCollisionObject collisionObject, short collisionFilterGroup, short collisionFilterMask) {
    gdxBulletJNI.btDiscreteDynamicsWorld_addCollisionObject__SWIG_0(swigCPtr, this, btCollisionObject.getCPtr(collisionObject), collisionObject, collisionFilterGroup, collisionFilterMask);
  }

  public void addCollisionObject(btCollisionObject collisionObject, short collisionFilterGroup) {
    gdxBulletJNI.btDiscreteDynamicsWorld_addCollisionObject__SWIG_1(swigCPtr, this, btCollisionObject.getCPtr(collisionObject), collisionObject, collisionFilterGroup);
  }

  public void addCollisionObject(btCollisionObject collisionObject) {
    gdxBulletJNI.btDiscreteDynamicsWorld_addCollisionObject__SWIG_2(swigCPtr, this, btCollisionObject.getCPtr(collisionObject), collisionObject);
  }

  public void addRigidBody(btRigidBody body) {
    gdxBulletJNI.btDiscreteDynamicsWorld_addRigidBody__SWIG_0(swigCPtr, this, btRigidBody.getCPtr(body), body);
  }

  public void addRigidBody(btRigidBody body, short group, short mask) {
    gdxBulletJNI.btDiscreteDynamicsWorld_addRigidBody__SWIG_1(swigCPtr, this, btRigidBody.getCPtr(body), body, group, mask);
  }

  public void debugDrawConstraint(btTypedConstraint constraint) {
    gdxBulletJNI.btDiscreteDynamicsWorld_debugDrawConstraint(swigCPtr, this, btTypedConstraint.getCPtr(constraint), constraint);
  }

  public btTypedConstraint getConstraint(int index) {
    long cPtr = gdxBulletJNI.btDiscreteDynamicsWorld_getConstraint__SWIG_0(swigCPtr, this, index);
    return (cPtr == 0) ? null : new btTypedConstraint(cPtr, false);
  }

  public void applyGravity() {
    gdxBulletJNI.btDiscreteDynamicsWorld_applyGravity(swigCPtr, this);
  }

  public void setNumTasks(int numTasks) {
    gdxBulletJNI.btDiscreteDynamicsWorld_setNumTasks(swigCPtr, this, numTasks);
  }

  public void updateVehicles(float timeStep) {
    gdxBulletJNI.btDiscreteDynamicsWorld_updateVehicles(swigCPtr, this, timeStep);
  }

  public void setSynchronizeAllMotionStates(boolean synchronizeAll) {
    gdxBulletJNI.btDiscreteDynamicsWorld_setSynchronizeAllMotionStates(swigCPtr, this, synchronizeAll);
  }

  public boolean getSynchronizeAllMotionStates() {
    return gdxBulletJNI.btDiscreteDynamicsWorld_getSynchronizeAllMotionStates(swigCPtr, this);
  }

}
