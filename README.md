# flowist.io

An application focused on *keeping developers in flow* by reminding you what you were working on, allowing for collaboration without the cost.

Visit the site at: [flowist.io](https://flowist.io)
Frontend repo can be found [here](https://github.com/jwbaldwin/flowist-frontend)

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.


### This is the backend which exposes a set of RESTful api's that the frontend uses
 * With intent to support:
   * Slack bot
   * Chrome extension

------------------
#### DEVELOPMENT
```
$ ./gradlew bootRun
```

--------------
#### PRODUCTION
```
$ ./gradlew clean build
```
 * Then publish the jar to elastic-beanstalk

## Deployment

Deployed using AWS Elastic beanstalk to run the Spring-boot jar

## Contributing
 //TODO: Add CONTRIBUTING.md

Please read [CONTRIBUTING.md](https://gist.github.com/) for details on our code of conduct, and the process for submitting pull requests to us.

Naming for brranches follows the conventions below. All branches are made from _dev_.

```prefix/short-description-here```

#### Prefix
```
wip       Works in progress; stuff I know won't be finished soon
feat      Feature I'm adding or expanding
bug       Bug fix or experiment
junk      Throwaway branch created to experiment
```

## Authors

* **James Baldwin** (https://github.com/jwbaldwin)

See also the list of [contributors](https://github.com/your/project/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details

## Acknowledgments

* Thanks to anyone who helped
* Inspiration comes from my frustrating brain which never ceases to find faults in products.
