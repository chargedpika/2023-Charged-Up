package com.frc7153.math;

/**
 * Shared, static math utility class for operation used by a number of other packages here.
 * Not all math utils have been moved here yet.
 */
public class MathUtils {
    /**
     * Takes an angle and normalizes it to a range of 0 - 360
     * @param angle
     * @return The angle, normalizes
     */
    public static double normalizeAngle360(double angle) {
        return angle - (360.0 * Math.floor(angle / 360.0));
    }

    /**
     * Takes an angle and normalizes it to a range of -180 to 180
     * @param angle
     * @return The angle, normalized
     */
    public static double normalizeAngle180(double angle) {
        angle = normalizeAngle360(angle);
        if (angle > 180) { angle -= 360.0; }
        return angle;
    }

    /**
     * Clamps a speed to the specified range, assuming the lower bound is the opposite of the upper bound
     * @param value The input value
     * @param clamp The upper bound, and opposite of the lower bound
     * @return The clamped value
     */
    public static double symmetricClamp(double value, double clamp) {
        if (clamp < 0) { clamp = -clamp;}
        return Math.min(Math.max(value, -clamp), clamp);
    }
}
