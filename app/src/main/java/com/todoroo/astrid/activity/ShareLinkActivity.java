/** TODO: make this lightweight, don't extend the entire TaskListActivity */
package com.todoroo.astrid.activity;

import static org.tasks.intents.TaskIntents.getEditTaskStack;

import android.content.Intent;
import android.os.Bundle;
import com.todoroo.astrid.dao.TaskDao;
import com.todoroo.astrid.data.Task;
import com.todoroo.astrid.service.TaskCreator;
import javax.inject.Inject;
import org.tasks.injection.ActivityComponent;
import org.tasks.injection.InjectingAppCompatActivity;

/**
 * @author joshuagross
 *     <p>Create a new task based on incoming links from the "share" menu
 */
public final class ShareLinkActivity extends InjectingAppCompatActivity {

  @Inject TaskCreator taskCreator;
  @Inject TaskDao taskDao;

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    readIntent();
  }

  @Override
  public void inject(ActivityComponent component) {
    component.inject(this);
  }

  @Override
  protected void onNewIntent(Intent intent) {
    super.onNewIntent(intent);

    setIntent(intent);

    readIntent();
  }

  private void readIntent() {
    Intent intent = getIntent();
    String subject = intent.getStringExtra(Intent.EXTRA_SUBJECT);
    if (subject == null) {
      subject = "";
    }

    Task task = taskCreator.createWithValues(null, subject);
    if (task != null) {
      task.setNotes(intent.getStringExtra(Intent.EXTRA_TEXT));
      getEditTaskStack(this, null, task).startActivities();
    }
    finish();
  }
}
