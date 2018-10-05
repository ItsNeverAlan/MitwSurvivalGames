package mitw.survivalgames.ratings;

import java.text.DecimalFormat;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class PlayerCache {

	public static DecimalFormat format = new DecimalFormat("0.0");

	private UUID uuid;
	private int wins;
	private int kills;
	private int deaths;
	private int rating;
	private String ratingTop;

	public String getKDRString() {
		return "" + format.format(deaths == 0 ? (double) kills : ((double)kills / deaths));
	}

}
