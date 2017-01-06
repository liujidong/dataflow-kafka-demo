package utils;

/**
 * Created by Administrator on 2016/5/10.
 */
public class PropertiesStack {
    private static String twitterAPIKey;
    private static String twitterAPISecret;
    private static String twitterAccessToken;
    private static String twitterAccessTokenSecret;

    private static String kafkaTopic="app_develop";
    private static String kafkaBootstrapServers="64.71.156.203:9092,64.71.156.204:9092,64.71.156.205:9092";
    //private static String kafkaBootstrapServers="182.254.133.69:9092";
    private static String kafkaTopicResult="topic_result";
    private static String kafkaGroupId="analytics_develop";

    //private static String zookeeperConnect="64.71.156.203:2181,64.71.156.204:2181,64.71.156.205:9092";
    private static String zookeeperConnect="182.254.133.69:2181";

    public static String getTwitterAPIKey() {
        return twitterAPIKey;
    }

    public static String getTwitterAPISecret() {
        return twitterAPISecret;
    }

    public static String getTwitterAccessToken() {
        return twitterAccessToken;
    }

    public static String getTwitterAccessTokenSecret() {
        return twitterAccessTokenSecret;
    }

    public static String getKafkaTopic() {
        return kafkaTopic;
    }

    public static String getKafkaBootstrapServers() {
        return kafkaBootstrapServers;
    }

    public static String getKafkaTopicResult() {
        return kafkaTopicResult;
    }

    public static String getKafkaGroupId() {
        return kafkaGroupId;
    }

    public static String getZookeeperConnect() {
        return zookeeperConnect;
    }
}
