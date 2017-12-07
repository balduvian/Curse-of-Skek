package engine;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Camera {
	
	private float width, height;
	private Vector3f position;
	private float rotation;
	private Matrix4f projection, projView;
	
	public Camera(float w, float h) {
		position = new Vector3f();
		rotation = 0;
		projView = new Matrix4f();
		setDims(w, h);
	}
	
	public void setDims(float w, float h) {
		//float halfW = w/2f;
		//float halfH = h/2f;
		//projection = new Matrix4f().setOrtho(-halfW,halfW,halfH,-halfH,1,-1);
		width = w;
		height = h;
		projection = new Matrix4f().setOrtho(0,w,h,0,1,-1);
	}
	
	public void update() {
		projection.translate(position.negate(new Vector3f()), projView);
		projView.rotateZ(-rotation);
	}
	
	public Matrix4f getProjview() {
		return new Matrix4f(projView);
	}
	
	public Matrix4f getProjection() {
		return new Matrix4f(projection);
	}
	
	public void move(float x, float y, float z) {
		position.add(x, y, z);
	}
	
	public void set(float x, float y, float z) {
		position.set(x, y, z);
	}
	
	public void set(Vector3f p) {
		position.set(p);
	}
	
	public void setCenter(Vector3f p) {
		position.set(p.sub(width/2, height/2, 0, new Vector3f()));
	}
	
	public void setRotation(float r) {
		rotation = r;
	}
	
	public void rotate(float r) {
		rotation += r;
	}
	
	public float getHeight() {
		return height;
	}
	
	public float getWidth() {
		return width;
	}
	
	public void setX(float x) {
		position.set(x, position.y, position.z);
	}
	
	public void setY(float y) {
		position.set(position.x, y, position.z);
	}
	
	public Vector3f getPosition() {
		return position;
	}
	
	public float getX() {
		return position.x;
	}
	
	public float getY() {
		return position.y;
	}
}
