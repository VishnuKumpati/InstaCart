import json
with open("content/curriculum/sequence.index.json") as f:
    idx = json.load(f)
week2 = [t for t in idx["topics"] if t.get("week_num") == 2]
for t in week2:
    prog_path = "content/" + t["path"] + "/topic.progress.json"
    with open(prog_path) as f:
        prog = json.load(f)
    g = prog["gates"]
    print(t["id"], prog["stage"],
          "hitl3=" + str(g.get("topic_artifacts_human_approved")),
          "drift=" + str(g.get("module_drift_passed")),
          "hitl4=" + str(g.get("week_human_approved")))
