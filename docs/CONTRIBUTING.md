# Contributing to Gamification Engine AMT

You want to contribute to Gamification Engine AMT? That's great, thanks! Here is some
help to get you started to contribute to the project.

_PS: This is a fairly new project and at the time of writing this documentation,
it is the first time setting up an open source project. If you have **any** tip
on how to manage this project, it is more than welcome!_

## Contribution workflow

You are free to work on your side by forking the project but if you want to
contribute in the same repository, please consider using this workflow:

0. Please check if any issue or merge request regarding your contribution has
   not already been filled!
1. Create a new issue describing the feature you would like to implement (do not
   forget the labels :)).
2. Just after that, create a new merge request by pressing the button "_Create
   merge request_". This will create a new WIP branch associated to the merge
   request.
3. Pull and switch to that new branch. Implement the new feature and push
   anytime you want.
4. When your new feature is ready to be merged with `master`, go back to the
   merge request and press "_Resolve WIP status_". This will check that you can
   merge with `master`.
5. If your feature can be merged to `master`, check the checkbox "_Remove source
   branch_". We do not want to keep old branchs in our repository.
6. You can then merge with master. This will automatically close the issue,
   delete the branch and your new feature is now on `master`! Congrats!

**Tips**

- Iterate with small issues at the time. The smaller feature you do, the better.
  Many things can change in the repository by the time you finish your work and
  the sooner other people can get your work, the better for them as well!
- `master` branch is protected. No one can push directly to `master`. This is a
  way we guarantee things on the `master` branch are stable and should be
  working.
- If you know that the feature you are working on will not pass the GitLab CI/CD
  pipeline, you can push with a commit message containing `[ci skip]` or
  `[skip ci]`, using any capitalization and the pipeline will be skipped.

## Issues

Issues are the primary way we use for communication. Issues can be about
anything: new features, bugs as well as discussions, questions and other things.

We use labels to quickly identify issues and their content. Please use labels as
much and as accurate possible to describe the issue, it is very helpful!

