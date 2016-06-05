package com.ss.editor.control.transform;

import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.InputManager;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Transform;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.ss.editor.Editor;
import com.ss.editor.control.transform.SceneEditorControl.PickedAxis;
import com.ss.editor.util.GeomUtils;

/**
 * Реализация контролера манипулятора перемещения.
 *
 * @author Ronn
 */
public class MoveToolControl extends AbstractControl implements TransformControl {

    public static final String NODE_MOVE_X = "move_x";
    public static final String NODE_MOVE_Y = "move_y";
    public static final String NODE_MOVE_Z = "move_z";

    private static final Editor EDITOR = Editor.getInstance();

    /**
     * Контролер редактора сцены.
     */
    private final SceneEditorControl editorControl;

    /**
     * Поскость для определения перемещения.
     */
    private final Node collisionPlane;

    public MoveToolControl(final SceneEditorControl editorControl) {
        this.editorControl = editorControl;
        this.collisionPlane = editorControl.getCollisionPlane();
    }

    /**
     * @return плоскость для определения перемещения.
     */
    private Node getCollisionPlane() {
        return collisionPlane;
    }

    /**
     * @return контролер редактора сцены.
     */
    private SceneEditorControl getEditorControl() {
        return editorControl;
    }

    @Override
    public void setCollisionPlane(final CollisionResult collisionResult) {

        final Camera camera = EDITOR.getCamera();
        final Vector3f direction = camera.getDirection();

        final SceneEditorControl editorControl = getEditorControl();
        final Transform transform = editorControl.getTransformCenter();
        final Quaternion rotation = transform.getRotation();

        // Set PickedAxis
        final Geometry geometry = collisionResult.getGeometry();
        final String geometryName = geometry.getName();

        if (geometryName.contains(NODE_MOVE_X)) {
            editorControl.setPickedAxis(PickedAxis.X);
        } else if (geometryName.contains(NODE_MOVE_Y)) {
            editorControl.setPickedAxis(PickedAxis.Y);
        } else if (geometryName.contains(NODE_MOVE_Z)) {
            editorControl.setPickedAxis(PickedAxis.Z);
        }

        final PickedAxis pickedAxis = editorControl.getPickedAxis();

        // select an angle between 0 and 90 degrees (from 0 to 1.57 in radians) (for collisionPlane)
        float angleX = direction.angleBetween(rotation.mult(Vector3f.UNIT_X));

        if (angleX > 1.57) {
            angleX = direction.angleBetween(rotation.mult(Vector3f.UNIT_X).negateLocal());
        }

        float angleY = direction.angleBetween(rotation.mult(Vector3f.UNIT_Y));

        if (angleY > 1.57) {
            angleY = direction.angleBetween(rotation.mult(Vector3f.UNIT_Y).negateLocal());
        }

        float angleZ = direction.angleBetween(rotation.mult(Vector3f.UNIT_Z));

        if (angleZ > 1.57) {
            angleZ = direction.angleBetween(rotation.mult(Vector3f.UNIT_Z).negateLocal());
        }

        //select the less angle for collisionPlane
        float lessAngle = angleX;

        if (lessAngle > angleY) lessAngle = angleY;
        if (lessAngle > angleZ) lessAngle = angleZ;

        // set the collision Plane location and rotation
        final Node collisionPlane = getCollisionPlane();
        collisionPlane.setLocalTranslation(transform.getTranslation());
        collisionPlane.setLocalRotation(rotation); //equals to angleZ

        final Quaternion planeRotation = collisionPlane.getLocalRotation();
        final Quaternion tempRotation = new Quaternion();

        // rotate the plane for constraints
        if (lessAngle == angleX) {

            if (pickedAxis == PickedAxis.X && angleY > angleZ) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Z));
            } else if (pickedAxis == PickedAxis.X && angleY < angleZ) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Z));
            } else if (pickedAxis == PickedAxis.Y) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y));
            } else if (pickedAxis == PickedAxis.Z) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y));
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Z));
            }

        } else if (lessAngle == angleY) {

            if (pickedAxis == PickedAxis.X) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Z));
            } else if (pickedAxis == PickedAxis.Y && angleX < angleZ) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y));
            } else if (pickedAxis == PickedAxis.Z) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
            }

        } else if (lessAngle == angleZ) {

            if (pickedAxis == PickedAxis.X) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Z));
            } else if (pickedAxis == PickedAxis.Z && angleY < angleX) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_X));
            } else if (pickedAxis == PickedAxis.Z && angleY > angleX) {
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Y));
                planeRotation.multLocal(tempRotation.fromAngleAxis(FastMath.HALF_PI, Vector3f.UNIT_Z));
            }
        }
    }

    @Override
    public void processTransform() {

        final SceneEditorControl editorControl = getEditorControl();

        final Camera camera = EDITOR.getCamera();
        final InputManager inputManager = EDITOR.getInputManager();
        final Vector2f cursorPosition = inputManager.getCursorPosition();

        final CollisionResults results = new CollisionResults();

        final Vector3f position = camera.getWorldCoordinates(cursorPosition, 0f).clone();
        final Vector3f direction = camera.getWorldCoordinates(cursorPosition, 1f).clone();
        direction.subtractLocal(position).normalizeLocal();

        final Ray ray = new Ray();
        ray.setOrigin(position);
        ray.setDirection(direction);

        final Node collisionPlane = getCollisionPlane();
        collisionPlane.collideWith(ray, results);

        final CollisionResult result = results.getClosestCollision();

        // Complex trigonometry formula based on sin(angle)*distance
        if (result != null) {

            Vector3f contactPoint = result.getContactPoint(); // get a point of collisionPlane

            final Transform transformCenter = editorControl.getTransformCenter();

            //set new deltaVector if it's not set
            if (editorControl.getDeltaVector() == null) {
                editorControl.setDeltaVector(transformCenter.getTranslation().subtract(contactPoint));
            }

            contactPoint = contactPoint.add(editorControl.getDeltaVector()); // add delta of the picked place

            Vector3f vec1 = contactPoint.subtract(transformCenter.getTranslation());
            float distanceVec1 = transformCenter.getTranslation().distance(contactPoint);

            // Picked vector
            PickedAxis pickedAxis = editorControl.getPickedAxis();
            Vector3f pickedVec = Vector3f.UNIT_X;

            if (pickedAxis == PickedAxis.Y) {
                pickedVec = Vector3f.UNIT_Y;
            } else if (pickedAxis == PickedAxis.Z) {
                pickedVec = Vector3f.UNIT_Z;
            }

            // the main formula for constraint axis
            float angle = vec1.normalize().angleBetween(transformCenter.getRotation().mult(pickedVec).normalizeLocal());
            float distanceVec2 = distanceVec1 * FastMath.sin(angle);

            // fix if angle>90 degrees
            Vector3f perpendicularVec = collisionPlane.getLocalRotation().mult(Vector3f.UNIT_X).mult(distanceVec2);
            Vector3f checkVec = contactPoint.add(perpendicularVec).subtractLocal(contactPoint).normalizeLocal();

            float angleCheck = checkVec.angleBetween(vec1.clone().normalizeLocal());

            if (angleCheck < FastMath.HALF_PI) {
                perpendicularVec.negateLocal();
            }

            // find distance to mave
            float distanceToMove = contactPoint.add(perpendicularVec).distance(transformCenter.getTranslation());
            distanceToMove = TransformConstraint.constraintValue(distanceToMove, TransformConstraint.getMoveConstraint());

            // invert value if it's needed for negative movement
            if (angle > FastMath.HALF_PI) {
                distanceToMove = -distanceToMove;
            }

            translateObjects(distanceToMove, pickedAxis, editorControl.getToTransform(), transformCenter);
        }
    }

    /**
     * Процесс перемещения объектов.
     *
     * @param distance       дистанция перемещения.
     * @param pickedAxis     ось перемещения.
     * @param toTransform    объект для перемещения.
     * @param selectedCenter центр трансформации.
     */
    private void translateObjects(final float distance, final PickedAxis pickedAxis, final Spatial toTransform, final Transform selectedCenter) {

        final Vector3f currentLocation = selectedCenter.getTranslation();
        final Quaternion currentRotation = selectedCenter.getRotation();

        if (pickedAxis == PickedAxis.X) {
            currentLocation.addLocal(GeomUtils.getLeft(currentRotation, null).mult(distance));
        } else if (pickedAxis == PickedAxis.Y) {
            currentLocation.addLocal(GeomUtils.getUp(currentRotation, null).mult(distance));
        } else if (pickedAxis == PickedAxis.Z) {
            currentLocation.addLocal(GeomUtils.getDirection(currentRotation, null).mult(distance));
        }

        toTransform.setLocalTranslation(currentLocation);

        final SceneEditorControl editorControl = getEditorControl();
        editorControl.notifyTransformed(toTransform);
    }

    @Override
    protected void controlUpdate(final float tpf) {
    }

    @Override
    protected void controlRender(final RenderManager renderManager, final ViewPort viewPort) {
    }
}
