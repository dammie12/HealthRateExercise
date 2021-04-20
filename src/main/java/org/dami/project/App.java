package org.dami.project;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class App {
	public static void main(String[] args) {
		final String filePath = "https://gist.githubusercontent.com/george-koneksa/176f0b6025f82248f740e70e4d906462/raw/90ef7a2241716ece97d33666a757c868bcbb0ee5/heart-rate.json";
		try {
			URL url = new URL(filePath);

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.registerModule(new JavaTimeModule());
			objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

			URLConnection request = url.openConnection();
			BufferedReader in = new BufferedReader(
					new InputStreamReader(request.getInputStream(), Charset.forName("UTF-8")));

			String jsonString = in.lines().collect(Collectors.joining());
			List<InitialData> listCar = objectMapper.readValue(jsonString, new TypeReference<List<InitialData>>() {
			});
			/* sort by date */
			listCar.sort(Comparator.comparing(InitialData::getStart_time));

			Set<String> set = new HashSet<>(listCar.size());

			/* get number of unique dates */
			List<InitialData> uniqueDates2 = listCar.stream().filter(p -> set.add(p.getStart_date().toString()))
					.collect(Collectors.toList());

			/* Group by date */

			ArrayList<HeartRate> allResult = new ArrayList<HeartRate>();
			ArrayList<ArrayList<InitialData>> mainList = new ArrayList<ArrayList<InitialData>>();
			for (int i = 0; i < uniqueDates2.size(); i++) {
				ArrayList<InitialData> tempList = new ArrayList<>();
				for (int j = 0; j < listCar.size(); j++) {
					if (uniqueDates2.get(i).getStart_date().equals(listCar.get(j).getStart_date())) {
						tempList.add(listCar.get(j));
					}
				}
				mainList.add(tempList);
			}

			/* Add data to HeartRate class */
			for (int i = 0; i < mainList.size(); i++) {
				HeartRate res = new HeartRate();
				BloodPressureData bmp = new BloodPressureData();
				List<InitialData> date = mainList.get(i).stream().filter(item -> item.getStart_date() != null)
						.distinct().collect(Collectors.toList());

				res.setDate(date.get(0).getStart_date());

				Optional<InitialData> minimum = mainList.get(i).stream().min(Comparator.comparing(InitialData::getBpm));

				bmp.setMin(minimum.get().getBpm());

				Optional<InitialData> maximum = mainList.get(i).stream().max(Comparator.comparing(InitialData::getBpm));

				bmp.setMax(maximum.get().getBpm());

				List<InitialData> allBpm = mainList.get(i).stream().filter(item -> item.getBpm() != 0)
						.collect(Collectors.toList());
				ArrayList<Integer> intBpm = new ArrayList<>();
				for (int j = 0; j < allBpm.size(); j++) {
					intBpm.add(allBpm.get(j).getBpm());
				}

				bmp.setMedian(getMedianValue(intBpm));
				res.setBpm(bmp);
				allResult.add(res);
			}
			in.close();
			System.out.println(objectMapper.writeValueAsString(allResult.listIterator()));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private static int getMedianValue(ArrayList<Integer> bpmList) {
		int count = bpmList.size();
		int remainder = count % 2;
		int mediumLength, mediumValue;
		if (remainder == 1) {
			mediumLength = (count - 1) / 2;
			mediumValue = bpmList.get(mediumLength);
		} else {
			mediumLength = count / 2;
			mediumValue = (bpmList.get(mediumLength - 1) + bpmList.get(mediumLength)) / 2;
		}

		return mediumValue;
	}
}
