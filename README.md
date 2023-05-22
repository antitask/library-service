# DOMAIN
- book library

## USE CASES
- as a public user I would like to see list of all books available in the library and filter them by title, author, ISBN number or availability status. 
- as a authenticated librarian I should be able to add or remove a book to library with all required data. Adding new book requires uploading physical copy of book to file system and updating database with book data. Removing book means removing only data from the database.
- as a authenticated user I would like to borrow or return a book from the library. After I choose available book I should be able to borrow or return a book.


## TECHNICAL REQUIREMENTS
- Checkout your branch using git from the task branch with the following pattern: LIB-{your name}
- book schema should contain id, title, author, ISBN, availability, date borrowed and optionally username or user id.
- authentication is required for users and should be simple. User security data (username/password) should be hard-coded and fetched from the dedicated table. Password should be plain text.
- Use Spring boot v2.7.7, test database, Maven, git and Postman.
- seed initially books data with at least 10 books and different availability status.
- implement aggregated book table without relations (store all book data in one table)
- cover your implementatin with unit tests
- use code formatting
- use common coding practices and pattern 
- comment your implementation
- make sure implementation is working locally before commit
- push commits incrementally (feature by feature)

## BONUS POINTS
- implement borrow limit for each book (in days)
- filter books by date borrowed and comparing to borrow limit in days
- filter books by user who borrow the book.
- prevent user from borrowing a book if previos book is not returned 


# IMPORTANT NOTE RELATED TO GIT COMMITS
- everyone except 84m.aleksandar@gmail.com should push incremental commits meaning whenever there is a new tested feature, it should be commited
- 84m.aleksandar@gmail.com should bulk push the whole project after it is finished
- don't create merge requests, only use your branch to push commits
