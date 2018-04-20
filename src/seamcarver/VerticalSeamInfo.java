package seamcarver;

// represents a vertical seam info
class VerticalSeamInfo extends ASeamInfo {

	VerticalSeamInfo(APixel pixel, double energy) {
		super(pixel, energy);
	}

	VerticalSeamInfo(APixel pixel, ASeamInfo parent1, ASeamInfo parent2, ASeamInfo parent3) {
		super(pixel, parent1, parent2, parent3);
	}

	// removes the seam vertically, remembers the first pixel on this seam
	// ACCUMULATOR: remembers the start of this seam
	@Override
	void remove(APixel start) {
		if (this.cameFrom != null) {
			this.pixel.removeVertically(this.cameFrom.pixel, start);
			this.cameFrom.remove(start);
		} else {
			this.pixel.removeVertically(null, start);
		}
	}

	// estimates the color for each pixel using its left and right pixels
	// EFFECT: changes the color of each pixel in this seam to an estimate
	void estimateColor() {
		if (this.cameFrom != null) {
			this.pixel.estimateColorVertically(this.cameFrom.pixel);
			this.cameFrom.estimateColor();
		} else {
			this.pixel.estimateColorVertically(null);
		}
	}

	// since this is a vertical seam, it has a width of 1
	@Override
	int width() {
		return 1;
	}

	// since this is a vertical seam, it has a height of 0
	@Override
	int height() {
		return 0;
	}

}
