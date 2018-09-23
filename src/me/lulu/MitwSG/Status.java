package me.lulu.MitwSG;

public enum Status {
	WAITING(false), STARRTING(false), GAMING(false), DEATHMATCH(false), DMSTARTING(false), FINISH(false);

	Main main;
	private boolean canJoin;
	private static Status currentState;

	private Status(boolean bl2) {
		this.canJoin = bl2;
	}

	public boolean canJoin() {
		return this.canJoin;
	}

	public static void setState(Status b) {
		currentState = b;
	}

	public static boolean isState(Status b) {
		return currentState == b;
	}

	public static Status getState() {
		return currentState;
	}

	public static boolean isWaiting() {
		if (currentState.equals(Status.WAITING))
			return true;
		return false;
	}

	public static boolean isStarting() {
		if (currentState.equals(Status.STARRTING))
			return true;
		return false;
	}

	public static boolean isDmStarting() {
		if (currentState.equals(Status.DMSTARTING))
			return true;
		return false;
	}

	public static boolean isDeathMatch() {
		if (currentState.equals(Status.DEATHMATCH))
			return true;
		return false;
	}

	public static boolean isFinished() {
		if (currentState.equals(Status.FINISH))
			return true;
		return false;
	}

	public static boolean isGaming(boolean incloudDm) {
		if (incloudDm) {
			if (currentState.equals(Status.GAMING) || currentState.equals(Status.DEATHMATCH)) {
				return true;
			}
		} else if (currentState.equals(Status.GAMING))
			return true;
		return false;
	}
}
