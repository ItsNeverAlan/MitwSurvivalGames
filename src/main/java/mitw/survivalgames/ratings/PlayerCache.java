package mitw.survivalgames.ratings;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PlayerCache {

	private String uuid;
	private int wins;
	private int kills;
	private int deaths;
	private int rating;

}
