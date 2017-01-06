package dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.io.Read;
import com.google.cloud.dataflow.sdk.options.DataflowPipelineOptions;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.transforms.ParDo;
import com.google.cloud.dataflow.sdk.transforms.windowing.AfterWatermark;
import com.google.cloud.dataflow.sdk.transforms.windowing.FixedWindows;
import com.google.cloud.dataflow.sdk.transforms.windowing.Window;
import com.google.cloud.dataflow.sdk.values.KV;
import com.google.cloud.dataflow.sdk.values.PCollection;
import org.joda.time.Duration;

import java.io.IOException;

public class TestUnboundedSource {

	static {

	    System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "DEBUG");
	}
	
	public static void main(String[] args) throws IOException {
		DataflowPipelineOptions options = PipelineOptionsFactory.create().as(DataflowPipelineOptions.class);
//		options.setRunner(DataflowPipelineRunner.class);
//		options.setProject("vuclipdataflow-1301");
//		options.setStagingLocation("gs://vuclip-dataflow2/test");
//		options.setStreaming(Boolean.TRUE);
//		options.setMaxNumWorkers(1);
//		options.setNumWorkers(1);
		Pipeline p = Pipeline.create(options);
		p.begin();
		KafkaUnboundedSource source = new KafkaUnboundedSource();
		System.out.println("===================================================");
		PCollection<String> input = p.apply(Read.from(source).named("KafkaUnboundedSource").withMaxNumRecords(100));
		System.out.println("--------------------------------------------------------------");
		PCollection<String> windowInput = input
				// .apply(Window.<String>
				// into(FixedWindows.of(Duration.standardMinutes(1))));
				.apply(Window.<String> into(FixedWindows.of(Duration.standardSeconds(10)))
						.triggering(
								AfterWatermark.pastEndOfWindow()
								//.plusDelayOf(Duration.standardMinutes(1))
								)
						.discardingFiredPanes().withAllowedLateness(Duration.ZERO));
		PCollection<KV<String, Long>> wordCounts = windowInput.apply(new WordCountTransform());
		PCollection<String> wordCountsStr = wordCounts.apply(new WordCountStringTransform());
		PCollection<Boolean> result = wordCountsStr.apply(ParDo.of(new WordCountWriterDoFn()));
		//wordCountsStr.apply(PubsubIO.Write.named("WriteToPubsub")
          //      .topic("projects/rapid-stream-118713/topics/tweets"));
		p.run();

	}

}
