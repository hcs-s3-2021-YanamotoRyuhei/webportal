package jp.ac.hcs.s3a329.weather;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WeatherController {
	@Autowired
	private WeatherService weatherService;
	/**
	 * 郵便番号から住所を検索し、結果画面を表示する
	 * @param weather 検索する郵便番号(ハイフン無し)
	 * @param prinicipal ログイン情報
	 * @param model
	 * @return 結果画面 - 郵便番号
	 */
	@GetMapping("/weather")
	public String getWeather(Principal principal, Model model) {
		String citycode = "016010";
		WeatherEntity weatherEntity = weatherService.getWeather(citycode);
		model.addAttribute("weatherEntity",weatherEntity);
		return "weather/weather";
		
	}
}
