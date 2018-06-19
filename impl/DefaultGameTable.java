package gomoku.impl;

import gomoku.game.gomoku.CellValue;
import gomoku.game.gomoku.GameTable;

public class DefaultGameTable implements GameTable {
	private final CellValue[][] gameTable;

	public DefaultGameTable() {
		gameTable = new CellValue[DefaultConstants.SIZE][DefaultConstants.SIZE];
		reInit();
	}

	public CellValue getValue(int row, int col) {
		if (row >= 0 && row < getSize() && col >= 0 && col < getSize()) {
			return gameTable[row][col];
		} else {
			throw new IndexOutOfBoundsException("Invalid row or col indexes: row=" + row + ", col=" + col + ", size=" + getSize());
		}
	}

	public void setValue(int row, int col, CellValue cellValue) {
		if (row >= 0 && row < getSize() && col >= 0 && col < getSize()) {
			gameTable[row][col] = cellValue;
		} else {
			throw new IndexOutOfBoundsException("Invalid row or col indexes: row=" + row + ", col=" + col + ", size=" + getSize());
		}
	}

	public int getSize() {
		return gameTable.length;
	}

	public void reInit() {
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				setValue(i, j, CellValue.EMPTY);
			}
		}
	}

	public boolean isCellFree(int row, int col) {
		return getValue(row, col) == CellValue.EMPTY;
	}

	public boolean emptyCellExists() {
		for (int i = 0; i < getSize(); i++) {
			for (int j = 0; j < getSize(); j++) {
				if (getValue(i, j) == CellValue.EMPTY) {
					return true;
				}
			}
		}
		return false;
	}
}
