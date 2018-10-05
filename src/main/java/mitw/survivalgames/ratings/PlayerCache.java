package mitw.survivalgames.ratings;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PlayerCache {

	private UUID uuid;
	private int wins;
	private int kills;
	private int deaths;
	private int rating;
	private String ratingTop;

}
