package mitw.survivalgames;

public enum GameStatus {
	WAITING(false), STARRTING(false), GAMING(false), DEATHMATCH(false), DMSTARTING(false), FINISH(false);

	SurvivalGames main;
	private boolean canJoin;
	private static GameStatus currentState;

	private GameStatus(final boolean bl2) {
		this.canJoin = bl2;
	}

	public boolean canJoin() {
		return this.canJoin;
	}

	public static void setState(final GameStatus b) {
		currentState = b;
	}

	public static boolean isState(final GameStatus b) {
		return currentState == b;
	}

	public static GameStatus getState() {
		return currentState;
	}

	public static boolean isWaiting() {
		if (currentState.equals(GameStatus.WAITING))
			return true;
		return false;
	}

	public static boolean isStarting() {
		if (currentState.equals(GameStatus.STARRTING))
			return true;
		return false;
	}

	public static boolean isDmStarting() {
		if (currentState.equals(GameStatus.DMSTARTING))
			return true;
		return false;
	}

	public static boolean isDeathMatch() {
		if (currentState.equals(GameStatus.DEATHMATCH))
			return true;
		return false;
	}

	public static boolean isFinished() {
		if (currentState.equals(GameStatus.FINISH))
			return true;
		return false;
	}

	public static boolean isGaming(final boolean incloudDm) {
		if (incloudDm) {
			if (currentState.equals(GameStatus.GAMING) || currentState.equals(GameStatus.DEATHMATCH))
				return true;
		} else if (currentState.equals(GameStatus.GAMING))
			return true;
		return false;
	}
}
