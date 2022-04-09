import java.util.List;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Ellipse;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsObject;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.events.Key;

/**
 * Tank part of the game. Contains the tanks and basic operations.
 */
public class Tank {
	private static final Integer MAX_CLIP_SIZE = 3;
	private static final double ROTATION_ANGLE = 6;
	private static final double TANK_RADIUS = 20;

	private double angle, dx, dy;
	private CanvasWindow canvas;
	private boolean type, status, wStatus, sStatus, aStatus, dStatus;
	private GraphicsGroup tank;
	private Key forward, backward, moveLeft, moveRight, launch;
	private GameMap currentMap;
	private List<Ball> tankClip;
	private boolean top, bot, right, left;

	/**
	 * Creates a tank with the input type and add it to the input canvas.
	 * 
	 * @param type       true represents tank type 1, and false represents tank type 2
	 * @param canvas     the canvas the the tank will be added to
	 * @param currentMap the map the tank is currently in
	 */
	public Tank(boolean type, CanvasWindow canvas, GameMap currentMap) {
		tank = new GraphicsGroup();
		tankClip = new ArrayList<>();

		this.currentMap = currentMap;
		this.canvas = canvas;
		this.type = type;
		angle = 0;
		status = false;
		wStatus = true;
		sStatus = true;
		aStatus = true;
		dStatus = true;
		dx = 3;
		dy = 3;
		draw();
	}

	/**
	 * Draws the specific tank and add it to a graphicgroup to operate.
	 */
	private void draw() {
		if (type) {
			Ellipse ellipse = new Ellipse(0, 0, 2 * TANK_RADIUS, 2 * TANK_RADIUS);
			ellipse.setFillColor(new Color(244, 184, 68));
			Line line = new Line(ellipse.getCenter().getX(), ellipse.getCenter().getY(),
				ellipse.getCenter().getX() + 20, ellipse.getCenter().getY());
			tank.add(ellipse);
			tank.add(line);
		} else {
			Ellipse ellipse = new Ellipse(0, 0, 2 * TANK_RADIUS, 2 * TANK_RADIUS);
			ellipse.setFillColor(new Color(123, 189, 244));
			Line line = new Line(ellipse.getCenter().getX(), ellipse.getCenter().getY(),
				ellipse.getCenter().getX() + 20, ellipse.getCenter().getY());
			tank.add(ellipse);
			tank.add(line);
		}
	}

	/**
	 * Moves the tank foreward/backward/rotates left and right.
	 */
	public void move() {
		if (type) {
			forward = Key.W;
			backward = Key.S;
			moveLeft = Key.A;
			moveRight = Key.D;
		} else {
			forward = Key.UP_ARROW;
			backward = Key.DOWN_ARROW;
			moveLeft = Key.LEFT_ARROW;
			moveRight = Key.RIGHT_ARROW;
		}
		if (canvas.getKeysPressed().contains(backward)) {
			wStatus = true;
			dStatus = true;
			tank.moveBy(-dx * Math.cos(Math.toRadians(angle)) * 0.75,
				dy * Math.sin(Math.toRadians(angle)) * 0.75);
		}
		if (canvas.getKeysPressed().contains(forward)) {
			sStatus = true;
			aStatus = true;
			tank.moveBy(dx * Math.cos(Math.toRadians(angle)), -dy * Math.sin(Math.toRadians(angle)));
		}
		if (canvas.getKeysPressed().contains(moveRight)) {
			tank.rotateBy(ROTATION_ANGLE);
			if (angle - ROTATION_ANGLE <= 0) {
				angle += 360;
				angle -= ROTATION_ANGLE;
			} else {
				angle -= ROTATION_ANGLE;
			}
		}
		if (canvas.getKeysPressed().contains(moveLeft)) {
			tank.rotateBy(-ROTATION_ANGLE);
			if (angle + ROTATION_ANGLE >= 360) {
				angle -= 360;
				angle += ROTATION_ANGLE;
			} else {
				angle += ROTATION_ANGLE;
			}
		}
		tankSlide();
	}

	/**
	 * Return a random initial top-left position for the tank.
	 */
	private Point createRandPos() {
		boolean randStatus = true;
		boolean ifRandPass = true;
		double randPosX = 0;
		double randPosY = 0;
		while (randStatus) {
			Random randPos = new Random();
			randPosX = (1200 - 1140 / 1.2) / 2 + 20 / 1.2
				+ (1140 / 1.2 - 40 / 1.2 - 20) * randPos.nextDouble();
			randPosY = 20 / 1.2 + (860 / 1.2 - 40 / 1.2 - 20) * randPos.nextDouble();
			for (Point pointIn : createRandList(new Point(randPosX + 20, randPosY + 20))) {
				if (canvas.getElementAt(pointIn) != null) {
					ifRandPass = false;
					break;
				} else {
					ifRandPass = true;
				}
			}
			if (ifRandPass) {
				randStatus = false;
			}
		}

		return new Point(randPosX, randPosY);
	}

	/**
	 * Create a list of 359 points around the tank according to the tank current top-left position.
	 */
	private List<Point> createRandList(Point point) {
		List<Point> pointList = new ArrayList<>();
		for (int i = 0; i < 360; i += 10) {
			pointList.add(new Point(point.getX() + Math.cos(i) * 30, point.getY() - Math.sin(i) * 30));
		}
		return pointList;
	}

	/**
	 * Press "Q" or PERIOD to fire the cannon ball.
	 */
	public void fire() {
		if (type) {
			launch = Key.Q;
		} else {
			launch = Key.PERIOD;
		}
		canvas.onKeyDown(event -> {
			if (event.getKey() == launch && tankClip.size() < MAX_CLIP_SIZE && status) {
				Ball ball = new Ball(angle, tank.getCenter(), tank.getHeight(), canvas, currentMap);
				tankClip.add(ball);
				ball.addToCanvas();
			}
		});
	}

	/**
	 * Interaction part of tank and the wall. Only four points (top, buttom, left, and right).
	 */
	private void tankSlide() {
		Point tankCenter = tank.getCenter();
		top = canvas.getElementAt(tankCenter.getX(), tankCenter.getY() - TANK_RADIUS - 1) != null &&
			canvas.getElementAt(tankCenter.getX(), tankCenter.getY() - TANK_RADIUS - 1) != tank;
		bot = canvas.getElementAt(tankCenter.getX(), tankCenter.getY() + TANK_RADIUS + 1) != null &&
			canvas.getElementAt(tankCenter.getX(), tankCenter.getY() + TANK_RADIUS + 1) != tank;
		left = canvas.getElementAt(tankCenter.getX() - TANK_RADIUS - 1, tankCenter.getY()) != null &&
			canvas.getElementAt(tankCenter.getX() - TANK_RADIUS - 1, tankCenter.getY()) != tank;
		right = canvas.getElementAt(tankCenter.getX() + TANK_RADIUS + 1, tankCenter.getY()) != null &&
			canvas.getElementAt(tankCenter.getX() + TANK_RADIUS + 1, tankCenter.getY()) != tank;
		if (!wStatus || !sStatus) {
			dy = 0;
		} else {
			dy = 3;
		}
		if (!dStatus || !aStatus) {
			dx = 0;
		} else {
			dx = 3;
		}
		if (top) {
			if (angle >= 0 && angle <= 180) {
				sStatus = true;
				wStatus = false;
			} else {
				sStatus = false;
				wStatus = true;
			}
		} else if (bot) {
			if (angle >= 180 && angle <= 360) {
				sStatus = true;
				wStatus = false;
			} else {
				sStatus = false;
				wStatus = true;
			}
		} else {
			sStatus = true;
			wStatus = true;
		}
		if (right) {
			if (angle <= 90 || angle >= 270) {
				aStatus = true;
				dStatus = false;
			} else {
				aStatus = false;
				dStatus = true;
			}
		} else if (left) {
			if (angle >= 90 && angle <= 270) {
				aStatus = true;
				dStatus = false;
			} else {
				aStatus = false;
				dStatus = true;
			}
		} else {
			aStatus = true;
			dStatus = true;
		}
	}

	/**
	 * Set the current game map of the tank to the input map.
	 * 
	 * @param map
	 */
	public void setCrtMap(GameMap map) {
		currentMap = map;
	}

	/**
	 * Reset the tank position randomly.
	 */
	public void resetPos() {
		tank.setPosition(createRandPos());
	}

	/**
	 * Return the GraphicsObject of the tank.
	 */
	public GraphicsObject getGraphics() {
		return tank;
	}

	/**
	 * Move the tank from canvas.
	 */
	public void removeFromCanvas() {
		canvas.remove(tank);
	}

	/**
	 * Retruns the clip of the tank.
	 */
	public List<Ball> getClip() {
		return tankClip;
	}

	/**
	 * Returns the current angle of the tank.
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * Returns the center position of the tank.
	 */
	public Point getCenter() {
		return tank.getCenter();
	}

	/**
	 * Returns the height of the tank.
	 */
	public double getHeight() {
		return tank.getHeight();
	}

	/**
	 * Returns the current status of the tank. True means alive, and false means dead.
	 */
	public boolean getStatus() {
		return status;
	}

	/**
	 * Returns the radius of the tank.
	 */
	public double getRadius() {
		return TANK_RADIUS;
	}

	/**
	 * Set the curret status of the tank with the input boolean.
	 * 
	 * @param val status of the tank
	 */
	public void setStatus(boolean val) {
		status = val;
	}

	@Override
	public String toString() {
		return "Tank [aStatus=" + aStatus + ", angle=" + angle + ", backward=" + backward + ", bot=" + bot + ", canvas="
			+ canvas + ", currentMap=" + currentMap + ", dStatus=" + dStatus + ", dx=" + dx + ", dy=" + dy
			+ ", forward=" + forward + ", launch=" + launch + ", left=" + left + ", moveLeft=" + moveLeft
			+ ", moveRight=" + moveRight + ", right=" + right + ", sStatus=" + sStatus + ", status=" + status
			+ ", tank=" + tank + ", tankClip=" + tankClip + ", top=" + top + ", type=" + type + ", wStatus=" + wStatus
			+ "]";
	}
}

