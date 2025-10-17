import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView rvTasks;
    FloatingActionButton btnAddTask;
    List<TaskModel> tasks;
    TaskAdapter adapter;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvTasks = findViewById(R.id.rvTasks);
        btnAddTask = findViewById(R.id.btnAddTask);
        prefs = getSharedPreferences("stask", MODE_PRIVATE);

        tasks = loadTasks();
        adapter = new TaskAdapter(tasks);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));
        rvTasks.setAdapter(adapter);

        btnAddTask.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, AddTaskActivity.class));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tasks.clear();
        tasks.addAll(loadTasks());
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Thêm");
        menu.add("Xóa tất cả");
        menu.add("Giới thiệu ứng dụng");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        if (title.equals("Thêm")) {
            startActivity(new Intent(this, AddTaskActivity.class));
        } else if (title.equals("Xóa tất cả")) {
            tasks.clear();
            saveTasks();
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "Đã xóa tất cả", Toast.LENGTH_SHORT).show();
        } else if (title.equals("Giới thiệu ứng dụng")) {
            Toast.makeText(this, "S-Task v1.0 - Thực hiện bởi [Tên bạn]", Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private List<TaskModel> loadTasks() {
        List<TaskModel> list = new ArrayList<>();
        try {
            String saved = prefs.getString("taskList", "");
            if (!saved.isEmpty()) {
                JSONArray arr = new JSONArray(saved);
                for (int i=0; i<arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    list.add(new TaskModel(obj.getString("title"), obj.getString("desc"), obj.getLong("due")));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    private void saveTasks() {
        try {
            JSONArray arr = new JSONArray();
            for (TaskModel t : tasks) {
                JSONObject obj = new JSONObject();
                obj.put("title", t.title);
                obj.put("desc", t.description);
                obj.put("due", t.dueTime);
                arr.put(obj);
            }
            prefs.edit().putString("taskList", arr.toString()).apply();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
