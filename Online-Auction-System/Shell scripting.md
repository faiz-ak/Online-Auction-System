#!/bin/bash

# Configuration

BACKUP*DIR="/mnt/c/Users/ajohn/Documents/backups/frontend_archives"
PROJECT_DIR="/home/john/your_project"
LOG_FILE="/home/john/frontend_archive.log"
TIMESTAMP=$(date +%Y%m%d*%H%M%S)
ARCHIVE*NAME="frontend_backup*${TIMESTAMP}.tar.gz"

# Ensure backup directory exists

mkdir -p "$BACKUP_DIR"
if [ $? -ne 0 ]; then
    echo "$(date '+%Y-%m-%d %H:%M:%S') - ERROR: Failed to create backup directory" >> "$LOG_FILE"
exit 1
fi

# Function to log messages

log_message() {
echo "$(date '+%Y-%m-%d %H:%M:%S') - $1" >> "$LOG_FILE"
}

# Function to check if command was successful

check_error() {
if [ $? -ne 0 ]; then
log_message "ERROR: $1"
exit 1
fi
}

# Start backup process

log_message "Starting frontend backup process"

# Create temporary directory for staging

TEMP_DIR=$(mktemp -d)
check_error "Failed to create temporary directory"

# Copy project files to temporary directory

cp -r "$PROJECT_DIR"/*.html "$TEMP_DIR"/ 2>/dev/null
check_error "Failed to copy HTML files"
cp -r "$PROJECT_DIR"/*.css "$TEMP_DIR"/ 2>/dev/null
check_error "Failed to copy CSS files"
cp -r "$PROJECT_DIR"/*.md "$TEMP_DIR"/ 2>/dev/null
check_error "Failed to copy Markdown files"
cp -r "$PROJECT_DIR"/*.txt "$TEMP_DIR"/ 2>/dev/null
check_error "Failed to copy Text files"

# Create tar.gz archive

tar -czf "$BACKUP_DIR/$ARCHIVE_NAME" -C "$TEMP_DIR" . 2>/dev/null
check_error "Failed to create archive"

# Clean up temporary directory

rm -rf "$TEMP_DIR"
check_error "Failed to clean up temporary directory"

# Remove backups older than 30 days

find "$BACKUP*DIR" -name "frontend_backup*\*.tar.gz" -mtime +30 -delete 2>/dev/null
check_error "Failed to remove old backups"

log_message "Backup completed successfully: $ARCHIVE_NAME"

# Verify archive was created

if [ -f "$BACKUP_DIR/$ARCHIVE_NAME" ]; then
log_message "Archive verification successful"
else
log_message "ERROR: Archive verification failed"
exit 1
fi

exit 0
