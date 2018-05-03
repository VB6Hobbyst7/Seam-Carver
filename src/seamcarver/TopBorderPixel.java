package seamcarver;

import java.util.ArrayList;
import java.util.Iterator;

// the top border pixel
class TopBorderPixel extends ATopBorderPixel {

	private final EmptyVerticalSeam emptySeam;
	private ColoredPixel bottom;
	private ABorderPixel left;
	private ATopBorderPixel right;

	TopBorderPixel(ABorderPixel left, ATopBorderPixel right, ColoredPixel bottom) {

		this(left, right);

		bottom.linkTopMutual(this);

	}

	// BE CAREFUL, initializes bottom with NULL, you better give it a value
	// later!
	TopBorderPixel(ABorderPixel left, ATopBorderPixel right) {

		left.linkRightMutual(this);
		right.linkLeftMutual(this);

		this.bottom = null;

		this.emptySeam = new EmptyVerticalSeam(this);

	}

	// just gives a array list containing this seam info
	@Override
	public ArrayList<AVerticalSeamInfo> collectVerticalSeamInfos() {
		ArrayList<AVerticalSeamInfo> seamInfos = new ArrayList<AVerticalSeamInfo>();

		seamInfos.add(this.emptySeam);

		return seamInfos;
	}

	@Override
	public void linkBottom(IPixel bottom) {
		bottom.linkTop(this);
	}

	@Override
	public void linkBottom(ColoredPixel bottom) {
		this.bottom = bottom;
	}

	@Override
	public void linkLeft(IPixel left) {
		left.linkRight(this);
	}

	@Override
	public void linkLeft(TopBorderPixel left) {
		this.left = left;
	}

	@Override
	public void linkLeft(TopLeftBorderPixel left) {
		this.left = left;
	}

	@Override
	public void linkRight(IPixel right) {
		right.linkLeft(this);
	}

	@Override
	public void linkRight(TopBorderPixel right) {
		this.right = right;
	}

	@Override
	public void linkRight(TopRightBorderPixel right) {
		this.right = right;
	}

	@Override
	public boolean isRight(IPixel pixel) {
		return pixel == this.right;
	}

	@Override
	public boolean isLeft(IPixel pixel) {
		return pixel == this.left;
	}

	@Override
	public boolean isBottom(IPixel pixel) {
		return pixel == this.bottom;
	}

	@Override
	TopBorderPixel asTopBorderPixel() {
		return this;
	}

	void remove() {
		this.right.linkLeftMutual(this.left);
	}
	
	TopBorderPixel duplicate() {
		return new TopBorderPixel(this.left, this, this.bottom);
	}

	void reinsert() {
		this.bottom.linkTop(this);
		this.left.linkRight(this);
		this.right.linkLeft(this);
	}

	HorizontalSeamInfo cheapestHorizontalSeam() {
		return this.bottom.cheapestHorizontalSeamInColumn();
	}
	
	// iterates through the column of colored pixels below this
	Iterable<ColoredPixel> columnIterable() {
		return this.bottom.columnIterable();
	}

	@Override
	public Iterator<TopBorderPixel> iterator() {
		return new TopBorderPixelIterator();
	}

	// an iterator for top border pixels, left to right
	private class TopBorderPixelIterator implements Iterator<TopBorderPixel> {

		private TopBorderPixel current;

		TopBorderPixelIterator() {
			this.current = TopBorderPixel.this;
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public TopBorderPixel next() {
			TopBorderPixel temp = this.current;
			this.current = this.current.right.asTopBorderPixel();
			return temp;
		}

	}

}