import 'package:flutter/material.dart';
import '../models/event.dart';

class EventCard extends StatelessWidget {
  final EventModel event;
  final VoidCallback? onEdit;
  final VoidCallback? onDelete;
  final VoidCallback? onAdd;

  EventCard({required this.event, this.onEdit, this.onDelete, this.onAdd});

  @override
  Widget build(BuildContext context) {
    return Card(
      child: ListTile(
        title: Text(event.title),
        subtitle: Text('${event.date} ${event.time}\n${event.location ?? ''}'),
        trailing: PopupMenuButton<String>(
          onSelected: (v) {
            if (v == 'edit') onEdit?.call();
            if (v == 'delete') onDelete?.call();
            if (v == 'add') onAdd?.call();
          },
          itemBuilder: (_) => [
            PopupMenuItem(value: 'edit', child: Text('Edit')),
            PopupMenuItem(value: 'delete', child: Text('Delete')),
            PopupMenuItem(value: 'add', child: Text('Add to Calendar')),
          ],
        ),
      ),
    );
  }
}
