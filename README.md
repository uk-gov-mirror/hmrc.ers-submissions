
# ERS-Submissions

[![Download](https://api.bintray.com/packages/hmrc/releases/ers-submissions/images/download.svg) ](https://bintray.com/hmrc/releases/ers-submissions/_latestVersion)

This microservice handles the submission of an ERS return to ADR.

When ERS-Submissions receives a call from ERS-File-Validator, it stores the data from the validated file (split into chunks) into Mongo as a pre-submission.
The user will then finish their journey on ERS-Returns-Frontend which will tell Submissions (via ERS-Returns) to submit the
data to ADR for processing.

For CSV files, ERS-Submissions uses Akka-streams to stream the file from upscan and store it into Mongo <br>
For ODS files, ERS-Submissions receives the file body from ERS-File-Validator as Json which is then stored into Mongo

For large files this service will not submit the file immediately, instead scheduling the submission to be at a later, less busy time.

### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
    