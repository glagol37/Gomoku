package game.gomoku.impl;

import java.util.ArrayList;
import java.util.Arrays;
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
		if (gameTable.getSize() < winCount) {
			throw new IllegalArgumentException(
					"Size of gameTable is small: size=" + gameTable.getSize() + ". Required >= " + winCount);
		}
		this.gameTable = gameTable;
	}

	public WinnerResult isWinnerFound(CellValue cellValue, Cell cell) {
		Objects.requireNonNull(cellValue, "cellValue can't be null");
		List<Cell> result = isWinnerByRow(cellValue, cell);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		result = isWinnerByCol(cellValue, cell);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		result = isWinnerByMainDiagonal(cellValue, cell);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		result = isWinnerByNotMainDiagonal(cellValue, cell);
		if (result != null) {
			return new DefaultWinnerResult(result);
		}
		return new DefaultWinnerResult(null);
	}

	protected List<Cell> isWinnerByRow(CellValue cellValue, Cell cell) {
		List<Cell> cells = new ArrayList<Cell>();
		for (int rowIndex = 0; rowIndex < gameTable.getSize(); rowIndex++) {
			if (gameTable.getValue(rowIndex, cell.getColIndex()) == cellValue) {
				cells.add(new Cell(rowIndex, cell.getColIndex()));
				if (cells.size() == winCount) {
					return cells;
				}
			} else {
				cells.clear();
				if (rowIndex > gameTable.getSize() - winCount) {
					break;
				}
			}
		}
		return null;
	}

	protected List<Cell> isWinnerByCol(CellValue cellValue, Cell cell) {
		List<Cell> cells = new ArrayList<Cell>();
		for (int colIndex = 0; colIndex < gameTable.getSize(); colIndex++) {
			if (gameTable.getValue(cell.getRowIndex(), colIndex) == cellValue) {
				cells.add(new Cell(cell.getRowIndex(), colIndex));
				if (cells.size() == winCount) {
					return cells;
				}
			} else {
				cells.clear();
				if (colIndex > gameTable.getSize() - winCount) {
					break;
				}
			}
		}
		return null;
	}

	protected List<Cell> isWinnerByMainDiagonal(CellValue cellValue,Cell cell) {
		int rowIndex = cell.getRowIndex();
		int colIndex = cell.getColIndex();
		while(true) {
			if(rowIndex == 0 || colIndex == 0) {
				break;
			}
			rowIndex--;
			colIndex--;
		}
		List<Cell> cells = new ArrayList<Cell>();
		for (int index = 0; index < gameTable.getSize(); index++) {
			if(rowIndex + index != gameTable.getSize() && colIndex + index !=gameTable.getSize()) {
				if (gameTable.getValue(rowIndex+index, colIndex+index) == cellValue) {
					cells.add(new Cell(rowIndex+index, colIndex+index));
					if (cells.size() == winCount) {
						return cells;
					}
				} else {
					cells.clear();
					if (colIndex > gameTable.getSize() - winCount) {
						break;
					}
				}
			} else { break; }
		}
		return null;
	}

	protected List<Cell> isWinnerByNotMainDiagonal(CellValue cellValue, Cell cell) {
		int rowIndex = cell.getRowIndex();
		int colIndex = cell.getColIndex();
		while(true) {
			if(rowIndex == gameTable.getSize()-1 || colIndex == 0) {
				break;
			}
			rowIndex++;
			colIndex--;
		}
		List<Cell> cells = new ArrayList<Cell>();
		for (int index = 0; index < gameTable.getSize(); index++) {
			if(rowIndex - index != 0 && colIndex + index !=gameTable.getSize()) {
				if (gameTable.getValue(rowIndex-index, colIndex+index) == cellValue) {
					cells.add(new Cell(rowIndex-index, colIndex+index));
					if (cells.size() == winCount) {
						return cells;
					}
				} else {
					cells.clear();
					if (colIndex > gameTable.getSize() - winCount) {
						break;
					}
				}
			} else { break; }
		}
		return null;
	}

	private static class DefaultWinnerResult implements WinnerResult {
		private final List<Cell> winnerCells;

		DefaultWinnerResult(List<Cell> result) {
			this.winnerCells = result != null ? newImmutableDataSet(result) : newImmutableDataSet(new Cell[0]);
		}

		public List<Cell> getWinnerCells() {
			return winnerCells;
		}

		public boolean winnerExists() {
			return winnerCells.size() > 0;
		}

		public static <Cell> List<Cell> newImmutableDataSet(final List<Cell> list) {
			return newImmutableDataSet((Cell[]) list.toArray());
		}

		public static <Cell> List<Cell> newImmutableDataSet(final Cell[] array) {
			List<Cell> list = Arrays.asList(array);
			return list;
		}
	}
}
