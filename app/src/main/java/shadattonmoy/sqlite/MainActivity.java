package shadattonmoy.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    SQLiteAdapter sqLiteAdapter;
    String name,regNo;
    EditText nameField,regNoField,nameForRegNoField,oldNameField,newNameField,deleteNameField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteAdapter = new SQLiteAdapter(this);

        nameField = (EditText)findViewById(R.id.name);
        regNoField = (EditText)findViewById(R.id.reg_no);
        nameForRegNoField = (EditText)findViewById(R.id.name_for_reg);
        oldNameField = (EditText)findViewById(R.id.old_name);
        newNameField = (EditText)findViewById(R.id.new_name);
        deleteNameField = (EditText)findViewById(R.id.name_to_delete);
    }

    public void addUser(View view) {
        name = nameField.getText().toString();
        regNo = regNoField.getText().toString();
        long result = sqLiteAdapter.insertData(name,regNo);

        if(result<0) {
            new Message(this,"Unsuccessful");
        }
        else {
            new Message(this,"Successful with row id "+result);
        }
    }

    public void getUser(View view) {
        String result = sqLiteAdapter.getAllData();
        new Message(this,result);
    }

    public void getRegNoForName(View view) {
        String name = nameForRegNoField.getText().toString();
        String result = sqLiteAdapter.getRegNo(name);
        if(result.equals("")) {
            new Message(this,"Sorry no records found!!!");
        }
        else new Message(this, result);
    }

    public void update(View view) {
        String oldName = oldNameField.getText().toString();
        String newName = newNameField.getText().toString();
        int result = sqLiteAdapter.update(oldName,newName);
        if(result>0) {
            new Message(this,result+" records have updated");
        }
        else new Message(this,"Nothing changed");
    }

    public void delete(View view) {
        String nameToDelete = deleteNameField.getText().toString();
        int result = sqLiteAdapter.delete(nameToDelete);
        if(result>0) {
            new Message(this, result+" rows have been deleted");
        }
        else new Message(this,"Nothing deleted");
    }
}
