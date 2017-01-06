package dataflow;

import com.google.cloud.dataflow.sdk.io.BoundedSource;
import com.google.cloud.dataflow.sdk.io.BoundedSource.BoundedReader;
import org.joda.time.Instant;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.NoSuchElementException;

public class FileBoundedReader extends BoundedReader<String> {

	//private KafkaConsumerObject consumer;
	private StringBuilder currentData;
	private BoundedSource<String> source;
	
	public FileBoundedReader(BoundedSource<String> source) {
		super();
		this.source = source;
		
	}

	@Override
	public boolean advance() throws IOException {
		File directory = new File("");//设定为当前文件夹
		System.out.println("==========================================");
		System.out.println(directory.getAbsolutePath());//获取绝对路径

		currentData = new StringBuilder();
		//FileReader fileReader = new FileReader("vanrikki-stool.txt");
//		BufferedReader bufferedReader = new BufferedReader(fileReader);

		//String url = "http://weather.yahooapis.com/forecastrss?p=02101";
		String url = "http://www.vuclip.com/";
		URLConnection conn = new URL(url).openConnection();
		InputStreamReader input = new InputStreamReader(conn.getInputStream(), "utf-8");
		BufferedReader bufferedReader = new BufferedReader(input);
		while (bufferedReader.ready()){
			String line = bufferedReader.readLine().trim();
			if (line.isEmpty())continue;
			currentData.append(line);
			currentData.append("\n");
		}
		bufferedReader.close();
		input.close();
		//fileReader.close();
		return false;
	}

	@Override
	public boolean start() throws IOException {
		advance();
//		consumer = new KafkaConsumerObject();
//		TopicPartition partition0 = new TopicPartition("tweets", 0);
//		consumer.assign(Arrays.asList(partition0));
//		consumer.seekToBeginning(partition0);
		return true;
	}

	@Override
	public void close() throws IOException {
//		if (consumer != null) {
//			consumer.close();
//		}

	}

	@Override
	public String getCurrent() throws NoSuchElementException {
		return currentData.toString();
	}

	@Override
	public Instant getCurrentTimestamp() throws NoSuchElementException {
		return Instant.now();
	}

	@Override
	public BoundedSource<String> getCurrentSource() {
		return this.source;
	}

}
