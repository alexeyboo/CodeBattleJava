package ru.codebattle.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import ru.codebattle.client.api.BoardPoint;
import ru.codebattle.client.api.Direction;
import ru.codebattle.client.api.TurnAction;
import ru.codebattle.client.mylogic.Attacking;
import ru.codebattle.client.mylogic.Surviving;

import static ru.codebattle.client.api.Direction.*;

public class Main {

	private static final String SERVER_ADDRESS = "http://codebattle2020final.westeurope.cloudapp.azure.com/codenjoy-contest/board/player/gmpt1dibg9syj35umr3t?code=2676475597175468131&gameName=bomberman";

	public static void main(String[] args) throws URISyntaxException, IOException {
		CodeBattleClient client = new CodeBattleClient(SERVER_ADDRESS);
		client.run(gameBoard -> {
			boolean attack = Attacking.doAttack(gameBoard);
			Direction move = Surviving.doMove(gameBoard);

			return new TurnAction(attack, move);
		});

		System.in.read();

		client.initiateExit();
	}
}
