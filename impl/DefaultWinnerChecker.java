package gomoku.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import gomoku.game.gomoku.Cell;
import gomoku.game.gomoku.CellValue;
import gomoku.game.gomoku.GameTable;
import gomoku.game.gomoku.WinnerChecker;
import gomoku.game.gomoku.WinnerResult;


public class DefaultWinnerChecker implements WinnerChecker {
	private GameTable gameTable;
	private int winCount = DefaultConstants.WIN_COUNT;
	
	public void setGameTable(GameTable gameTable) {
		Objects.requireNonNull(gameTable, "Game table can't be null");
		if(gameTable.getSize() < winCount) {
			throw new IllegalArgumentException("Size of gameTable is small: size="+gameTable.getSize()+". Required >= "+winCount);
		}
		this.gameTable = gameTable;
	}

	public WinnerResult isWinnerFound(CellValue cellValue) {
		Objects.requireNonNull(cellValue, "cellValue can't be null");
		List<Cell> result = isWinnerByRow(cellValue);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		result = isWinnerByCol(cellValue);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		result = isWinnerByMainDiagonal(cellValue);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		result = isWinnerByNotMainDiagonal(cellValue);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		return new DefaultWinnerResult(null);
	}

	protected List<Cell> isWinnerByRow(CellValue cellValue) {
		for (int i = 0; i < gameTable.getSize(); i++) {
			List<Cell> cells = new ArrayList<Cell>();
			for (int j = 0; j < gameTable.getSize(); j++) {
				if (gameTable.getValue(i, j) == cellValue) {
					cells.add(new Cell(i, j));
					if (cells.size() == winCount) {
						return cells;
					}
				} else {
					cells.clear();
					if (j > gameTable.getSize() - winCount) {
						break;
					}
				}
			}
		}
		return null;
	}

	protected List<Cell> isWinnerByCol(CellValue cellValue) {
		for (int i = 0; i < gameTable.getSize(); i++) {
			List<Cell> cells = new ArrayList<Cell>();
			for (int j = 0; j < gameTable.getSize(); j++) {
				if (gameTable.getValue(j, i) == cellValue) {
					cells.add(new Cell(j, i));
					if (cells.size() == winCount) {
						return cells;
					}
				} else {
					cells.clear();
					if (j > gameTable.getSize() - winCount) {
						break;
					}
				}
			}
		}
		return null;
	}

	protected List<Cell> isWinnerByMainDiagonal(CellValue cellValue) {
		int winCountMinus1 = winCount - 1;
		for (int i = 0; i < gameTable.getSize() - winCountMinus1; i++) {
			for (int j = 0; j < gameTable.getSize() - winCountMinus1; j++) {
				List<Cell> cells = new ArrayList<Cell>();
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i + k, j + k) == cellValue) {
						cells.add(new Cell(i + k, j + k));
						if (cells.size() == winCount) {
							return cells;
						}
					} else {
						break;
					}
				}
			}
		}
		return null;
	}

	protected List<Cell> isWinnerByNotMainDiagonal(CellValue cellValue) {
		int winCountMinus1 = winCount - 1;
		for (int i = 0; i < gameTable.getSize() - winCountMinus1; i++) {
			for (int j = winCountMinus1; j < gameTable.getSize(); j++) {
				List<Cell> cells = new ArrayList<Cell>();
				for (int k = 0; k < winCount; k++) {
					if (gameTable.getValue(i + k, j - k) == cellValue) {
						cells.add(new Cell(i + k, j - k));
						if (cells.size() == winCount) {
							return cells;
						}
					} else {
						break;
					}
				}
			}
		}
		return null;
	}

	private static class DefaultWinnerResult implements WinnerResult {
		private final List<Cell> winnerCells;

		DefaultWinnerResult(List<Cell> result) {
			this.winnerCells = result != null ? newImmutableDataSet(result):newImmutableDataSet(new Cell[0]) ;
		}

		public List<Cell> getWinnerCells() {
			return winnerCells;
		}

		public boolean winnerExists() {
			return winnerCells.size() > 0;
		}
		public static <Cell> List<Cell> newImmutableDataSet(final List<Cell> list) {
			return newImmutableDataSet((Cell[])list.toArray());
		}
		public static <Cell> List<Cell> newImmutableDataSet(final Cell[] array) {
			return new ArrayList<Cell>();
		}
	}
}
