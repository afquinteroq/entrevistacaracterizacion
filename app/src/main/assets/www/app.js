function sayhello(){
alert('hi, '+document.getElementById('name').value+'!');
}

var CreateTb1 = "CREATE TABLE IF NOT EXISTS tbl1(ID INTEGER PRIMARY KEY AUTOINCREMENT, CreatedDate TEXT,LastModifiedDate TEXT, Name TEXT)";
var CreateTb2 = "CREATE TABLE IF NOT EXISTS tbl2(ID INTEGER PRIMARY KEY AUTOINCREMENT, CreatedDate TEXT,LastModifiedDate TEXT,Mark INTEGER)";
var db = openDatabase("vivanto", "1.0", "Testing Purpose", 200000); // Open SQLite Database


function initDatabase() // Function Call When Page is ready.
{
  try
  {
    if (!window.openDatabase) // Check browser is supported SQLite or not.
    {
      alert('Databases are not supported in your device');
    }
    else
    {
      createTable(); // If supported then call Function for create table in SQLite
      alert('entro a crear tabla');
    }
  }
  catch (e)
  {
    if (e == 2)
    {
      // Version number mismatch.
      console.log("Invalid database version.");
      alert('Versin number mismatch');
    }
    else
    {
      console.log("Unknown error " + e + ".");
      console.log("Ultima validación");
    }
    return;
  }
}

function createTable() // Function for Create Table in SQLite.
{

  db.transaction(function(tx)
  {
    tx.executeSql(CreateTb1, [], tblonsucc, tblonError);
    tx.executeSql(CreateTb2, [], tblonsucc, tblonError);
    console.log("3333333333333333333333333333333333333333333333333333333333333333333");

    alert('3');



  }, tranonError, tranonSucc);
}

function tranonError(err)
{
  console.error("Error processing SQL: " + err.code);
  alert('4');
}

function tranonSucc()
{
  console.info("Transaction Success");
  alert('5');
}


