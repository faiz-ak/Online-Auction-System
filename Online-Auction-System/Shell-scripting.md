#!/bin/bash

# Configuration

BACKUP*DIR="/mnt/c/Users/ajohn/Documents/backups/frontend_archives"

PROJECT_DIR="/home/john/auctionsystem"
WINDOWS_SHARE="/mnt/c/Users/ajohn/Documents/Online-Auction-System-Update/Online-Auction-System-Update/Online-Auction-System/Online-Auction-System"

LOG_FILE="/home/john/frontend_archive.log"
TIMESTAMP=$(date +%Y%m%d*%H%M%S)
ARCHIVE*NAME="frontend_backup*${TIMESTAMP}.tar.gz"

# Ensure directories exist

mkdir -p "$PROJECT_DIR" "$BACKUP_DIR"
if [ $? -ne 0 ]; then
echo "$(date '+%Y-%m-%d %H:%M:%S') - ERROR: Failed to create directories" >> "$LOG_FILE"
=======


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


# Retry logic for sync

max_attempts=3
attempt=1
while [ $attempt -le $max_attempts ]; do
if [ -d "$WINDOWS_SHARE" ]; then
if cp -r "$WINDOWS_SHARE"/*.{html,css,md} "$PROJECT_DIR"/ 2>/dev/null; then
break
else
log_message "Attempt $attempt: Failed to sync files from Windows share"
            if [ $attempt -eq $max_attempts ]; then
                log_message "ERROR: Failed to sync files from Windows share after $max_attempts attempts"
                exit 1
            fi
            sleep 5  # Wait 5 seconds before retry
        fi
    else
        log_message "ERROR: Windows share $WINDOWS_SHARE not accessible"
        exit 1
    fi
    attempt=$((attempt + 1))
done

# Sync assets directory if present

if [ -d "$WINDOWS_SHARE/assets" ]; then
cp -r "$WINDOWS_SHARE/assets" "$PROJECT_DIR"/ 2>/dev/null || check_error "Failed to sync assets directory"
fi

# Create temporary directory for staging

TEMP_DIR=$(mktemp -d)
check_error "Failed to create temporary directory"

# Copy project files to temporary directory


cp -r "$PROJECT_DIR"/*.html "$TEMP_DIR"/ 2>/dev/null || check_error "Failed to copy HTML files"
cp -r "$PROJECT_DIR"/*.css "$TEMP_DIR"/ 2>/dev/null || check_error "Failed to copy CSS files"
cp -r "$PROJECT_DIR"/*.md "$TEMP_DIR"/ 2>/dev/null || check_error "Failed to copy Markdown files"

# Removed .txt copying since no such files exist

if [ -d "$PROJECT_DIR/assets" ]; then
cp -r "$PROJECT_DIR/assets" "$TEMP_DIR"/ 2>/dev/null || check_error "Failed to copy assets directory"
fi


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
