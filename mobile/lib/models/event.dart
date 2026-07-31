class EventModel {
  int? id;
  String title;
  String date; // yyyy-MM-dd
  String time; // HH:mm
  String? location;

  EventModel({this.id, required this.title, required this.date, required this.time, this.location});

  factory EventModel.fromJson(Map<String, dynamic> json) => EventModel(
    id: json['id'],
    title: json['title'],
    date: json['date'],
    time: json['time'],
    location: json['location'],
  );

  Map<String, dynamic> toJson() => {
    'id': id,
    'title': title,
    'date': date,
    'time': time,
    'location': location,
  };
}
