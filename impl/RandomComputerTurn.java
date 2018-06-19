package gomoku.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import gomoku.game.gomoku.Cell;
import gomoku.game.gomoku.CellValue;
import gomoku.game.gomoku.ComputerTurn;
import gomoku.game.gomoku.GameTable;


public class RandomComputerTurn implements ComputerTurn {
	private GameTable gameTable;
	
	
	public void setGameTable(GameTable gameTable) {
		Objects.requireNonNull(gameTable, "Game table can't be null");
		this.gameTable = gameTable;
	}

	
	public Cell makeTurn() {
		List<Cell> emptyCells = getAllEmptyCells();
		if (emptyCells.size() > 0) {
			Cell randomCell = emptyCells.get(new Random().nextInt(emptyCells.size()));
			gameTable.setValue(randomCell.getRowIndex(), randomCell.getColIndex(), CellValue.COMPUTER);
			return randomCell;
		} else {
			throw new ComputerCantMakeTurnException("All cells are filled! Have you checked draw state before call of computer turn?");
		}
	}

	
	public Cell makeFirstTurn() {
		return makeTurn();
	}

	protected List<Cell> getAllEmptyCells(){
		List<Cell> emptyCells = new ArrayList<Cell>();
		for (int i = 0; i < gameTable.getSize(); i++) {
			for (int j = 0; j < gameTable.getSize(); j++) {
				if (gameTable.isCellFree(i, j)) {
					emptyCells.add(new Cell(i, j));
				}
			}
		}
		return emptyCells;
	}
}
