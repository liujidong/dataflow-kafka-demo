package dataflow;

import com.google.cloud.dataflow.sdk.Pipeline;
import com.google.cloud.dataflow.sdk.io.Read;
import com.google.cloud.dataflow.sdk.io.Write;
import com.google.cloud.dataflow.sdk.options.PipelineOptions;
import com.google.cloud.dataflow.sdk.options.PipelineOptionsFactory;
import com.google.cloud.dataflow.sdk.transforms.SimpleFunction;
import com.google.cloud.dataflow.sdk.values.KV;
import com.google.cloud.dataflow.sdk.values.PCollection;

public class TestBoundedSource {
	public static class FormatAsTextFn extends SimpleFunction<KV<String, Long>, String> {
		//@Override
		public String apply(KV<String, Long> input) {
			return input.getKey() + ": " + input.getValue();
		}
	}
	public static void main(String[] args) {
//		 DataflowPipelineOptions options = PipelineOptionsFactory.create()
//		 .as(DataflowPipelineOptions.class);
//		 options.setRunner(BlockingDataflowPipelineRunner.class);
//		 options.setProject("vuclipdataflow-1301");
//		 options.setStagingLocation("gs://vuclip-dataflow2/test");
		PipelineOptions options = PipelineOptionsFactory.create();

		Pipeline p = Pipeline.create(options);
		p.begin();
		FileBoundedSource source = new FileBoundedSource();
		PCollection<String> input = p.apply(Read.from(source).named("boundedSource"));
		//p.apply(TextIO.Read.from("gs://dataflow-samples/shakespeare/kinglear.txt"));
		//apply(Read.from(source).named("boundedSource"));
//		PCollection<String> windowInput = input
//				.apply(Window.<String> into(FixedWindows.of(Duration.standardMinutes(1))));
		PCollection<KV<String, Long>> wordCounts = input.apply(new WordCountTransform());
		wordCounts.apply(Write.<KV<String, Long>>to(new FileSink()));
		//wordCounts.apply(MapElements.via(new FormatAsTextFn())).apply(TextIO.Write.named("WriteCounts").to("gs://vuclip-dataflow2/output"));
		p.run();
	}

}
