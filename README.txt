• Name and functionality of each of the ﬁles you
submitted

  files: App
    func: main()
        start the application by connect to db and s3bucket
        then take the user input
            - newuser <username> <password> <re-type password>
                if adding successfully
                    >> OK
                if username is already taken
                    >> cannot use this user : <username>
                if password and re-type password are not the same
                    >> password mismatch
            - login <username> <password>
                if there is the a user with this password
                    >> OK
                if the password is wrong
                    >> wrong password
                if there is no user match
                    >> this username is not in the system
            -

  files: Bucket
    func: createBucket()
        this function will create a bucket to be the dropbox
    func: addObjectToBucket()
        this function will take filename and username then store that file
        as username-filename in the bucket
    func: viewObjectsInBucket()
    func: downloadFile()

• How to run your code
    java -jar myDropbox_5681091.jar