# slack-integrations-core

This is the base implementation of the [GMJM slack-integrations-api](https://github.com/GreaterMKEMeetup/slack-integrations-api).  See the readme for API usage examples.

## Setup

### Maven
```xml
<dependencies>
	<dependency>
		<groupId>com.github.greatermkemeetup</groupId>
		<artifactId>slack-integrations-api</artifactId>
		<version>1.0.2</version>
	</dependency>
	<dependency>
		<groupId>com.github.greatermkemeetup</groupId>
		<artifactId>slack-integrations-core</artifactId>
		<version>1.0.2</version>
		...
	</dependency>
</dependencies>
```
### Gradle
```groovy
compile 'com.github.greatermkemeetup:slack-integrations-api:1.0.2'
compile 'com.github.greatermkemeetup:slack-integrations-core:1.0.2'
```

Here are a few ways to setup your implementation.  More concrete examples can be found in the [GMJM springboot-slack-integrations](https://github.com/GreaterMKEMeetup/springboot-slack-integrations) project.

```java
package org.gmjm;

import org.gmjm.slack.api.hook.HookRequest;
import org.gmjm.slack.api.hook.HookRequestFactory;
import org.gmjm.slack.api.message.SlackMessageFactory;
import org.gmjm.slack.core.hook.HttpsHookRequestFactory;
import org.gmjm.slack.core.message.JsonMessageFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan
@Configuration
@EnableAutoConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Value("${slack.webhook.url}")
    private String slackWebhookUrl;

    private HookRequestFactory hookRequestFactory = new HttpsHookRequestFactory();

    @Bean
    public HookRequestFactory getHookRequestFactory() {
        return hookRequestFactory;
    }

    @Bean
    public HookRequest getHookRequest() {
        return hookRequestFactory.createHookRequest(slackWebhookUrl);
    }

    @Bean
    public SlackMessageFactory getSlackMessageFactory() {
        return new JsonMessageFactory();
    }


}
```

## Usage

```java
@Service
public class SlackMessageProcessor {

  private static final Logger logger = LoggerFactory.getLogger(SlackMessageProcessor.class);

  @Autowired
  SlackMessageFactory slackMessageFactory;
  
  @Autowired
  HookRequestFactory hookRequestFactory;
  
  @Autowired
  HookRequest hookRequest;
  
  public void process(SlackCommand slackCommand) {
    SlackMessageBuilder messageBuilder = slackMessageFactory.createMessageBuilder()
		.setUsername("doughnut-overlord")
    	.setIconEmoji("doughnut")
    	.setText("Eat me, I'm a *delicious* doughnut!") //Markdown is enabled by default
    	.setChannelId(slackCommand.getChannelId());
    
    //Send a response only the user who issued the command will see.
    hookRequestFactory
      .create(slackCommand.getResponseUrl())
      .send("Your command is being processed");
    
    //Send a response to the entire channel.
    HookResponse response = hookRequest.send(messageBuilder.build());
    
    if(Status.FAILED.equals(hookResponse.getStatus())) {
      logger.error("Failed to send response: " + hookResponse.getMessage());
    }
    
  }

}
```
