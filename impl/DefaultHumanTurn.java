package game.gomoku.impl;

import java.util.Objects;

import gomoku.game.gomoku.Cell;
import gomoku.game.gomoku.CellValue;
import gomoku.game.gomoku.GameTable;
import gomoku.game.gomoku.HumanTurn;


public class DefaultHumanTurn implements HumanTurn{
	private GameTable gameTable;
	
	public void setGameTable(GameTable gameTable) {
		Objects.requireNonNull(gameTable, "Game table can't be null");
		this.gameTable = gameTable;
	}

	public Cell makeTurn(int row, int col) {
		gameTable.setValue(row, col, CellValue.HUMAN);
		return new Cell(row, col);
	}
}
