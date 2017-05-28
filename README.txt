• Name and functionality of each of the ﬁles you
submitted

  files: App

  files: Bucket
    func: createBucket()
        this function will create a bucket to be the dropbox
    func: addObjectToBucket()
        this function will take filename and username then store that file
        as username-filename in the bucket
    func: viewObjectsInBucket()
    func: downloadFile()

  files: MySQLJava
    func: readData()
        this function initialize the hashmap in
        this class to store all user from getResultSet() in that hashmap
    func: getResultSet()
        this function read all row in users table and transform it to be hashmap
        where username is key and password as value
    func: authen()
        put username and password and return whether it is mismatch,
        correct pass,or no username
    func: addUser()
        put in username and password and store it in db
    func: insertDB()
        called by addUser() to actually insert username and password to db
    func: hasUser()
        check with hashmap whether there is username or not
    func: close()
        close connection to db

• How to run your code
    java -jar myDropbox_5681091.jar