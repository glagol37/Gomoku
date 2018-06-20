package gomoku.game.gomoku;


public interface WinnerChecker {
	
	void setGameTable(GameTable gameTable);

	WinnerResult isWinnerFound(CellValue cellValue);
}
