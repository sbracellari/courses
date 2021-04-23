# Table of Contents

- [Courses Soffit](#courses-soffit)
- [Location and Interaction](#location-and-interaction)
- [Project Setup](#project-setup)
- [Build Processes](#build-processes)
- [Service URL](#service-url)

## Courses Soffit

Courses Soffit is an application that allows the end user to view all of their course related data, i.e. course instructors, description, and meeting times, as well as their course grades and their overall GPA. Courses Soffit also provides a calendar view of the user's courses, an option to link directly to the University's Barnes and Noble website to view required course textbooks, and an option to save courses to a PDF for printing. All data except number of credits and overall GPA is provided based on a selected semester.

## Location and Interaction

In uPortal, Courses Soffit lives under the `Welcome` and `Academics` tabs, just under `My Details`. Its target audience is students of Oakland University (essentially anyone who is taking, has taken, or will take courses in the near future).

## Project Setup

Courses Soffit is a tad different from our other soffits in that the frontend of this project lives in [Oakland University's GitHub repository](https://github.com/Oakland-University/courses). In the root of this project, if you navigate to `src/main/react` you'll see that it is pointing to a commit hash (it will look something like `react@c9293c5c`). That commit hash belongs to the Github repository that houses all of the frontend code for this project.

Initial setup of this project will require you to run the following command:

```
git submodule init && git submodule update --remote
```

This will initialize the `src/main/react` from the [`.gitmodules` file](./.gitmodules/), fetch all the of data from that project, and check out the appropriate commit listed in the superproject (which will be the latest commit in the master branch of the Oakland University Github project).

## Build Processes

To build this project with demo data, navigate to `src/main/react/public/index.html` and change `is_demo` to `true`. The application will now run with demo data. The demo data itself can be found in `src/main/react/src/api`. Once you've changed `is_demo` to `true`, you can run an `npm i && npm start` in `src/main/react` and interact with the frontend without running the backend.

If you want to run the frontend and backend with real data but outside of uPortal, ensure that `is_demo` in `/public/index.html` is `false`, and then navigate to `src/main/react/src/actions`. In each file in the `actions` folder, you will find a url, named either `courses_url` or `events_url`. Remove the prepended `/courses-soffit` from each url. Once that is done, you can run a `./gradlew clean bootRun` in the root of the project, and an `npm i && npm start` in the `src/main/react` folder. This will allow you to interact with the frontend while the backend provides you with real data.

To build this project in preparation for deployment to uPortal, you'll want to ensure that `is_demo` is false, and that each url in the files in the `actions` folder is prepended with `courses-soffit`. Then run a `./gradlew clean build` in the root of the project, and follow [this](https://code.oakland.edu/ea-developers/training/-/tree/master/mysail/soffits#deploying-a-soffit-to-uportal) guide to deploy the soffit if you don't know how to already.

> **NOTE:** You can also deploy the soffit with demo data, if you wish to do so.

## Service URL

`/courses-soffit/soffit/courses-soffit`
