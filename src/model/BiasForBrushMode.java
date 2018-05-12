package model;

import java.util.Collection;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import seamcarver.SeamCarver;

// a brush mode for biasing for pixels
public class BiasForBrushMode implements IBrushMode {

	private static final Color COLOR = Color.rgb(0, 255, 0, 0.75);

	@Override
	public Color color() {
		return COLOR;
	}

	@Override
	public void finish(SeamCarver seamCarver, Collection<Point2D> points) {
		seamCarver.biasForPixels(points);
	}

	@Override
	public void undo(SeamCarver seamCarver, Collection<Point2D> points) {
		seamCarver.unbiasPixels(points);
	}

	@Override
	public String toString() {
		return "Bias For";
	}

}