package com.bw.teamspoc;

import com.bw.teamspoc.entities.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slack.api.model.Im;
import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.slack.api.socket_mode.SocketModeClient.LOGGER;

public class SendMessageToTeams {


    private enum Severity {
        high("ff0000"),
        medium("ffff00"),
        low("00ff00");

        private final String colourHexCode;

        private Severity(final String colourHexCode) {
            this.colourHexCode = colourHexCode;
        }

        public String getColourHexCode() {
            return  colourHexCode;
        }
    }

    public static void main(String[] args) {
//        HeroImage image = HeroImage.builder().image("https://hddesktopwallpapers.in/wp-content/uploads/2015/08/duck-cute-wallpaper.jpg").build();
        Image im2 = Image.builder().image("data:image/gif;base64,R0lGODlhEAAQAMQAAORHHOVSKudfOulrSOp3WOyDZu6QdvCchPGolfO0o/XBs/fNwfjZ0frl3/zy7////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAABAALAAAAAAQABAAAAVVICSOZGlCQAosJ6mu7fiyZeKqNKToQGDsM8hBADgUXoGAiqhSvp5QAnQKGIgUhwFUYLCVDFCrKUE1lBavAViFIDlTImbKC5Gm2hB0SlBCBMQiB0UjIQA7\n").build();
        Image im3 = Image.builder().image("data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEASABIAAD/4gxYSUNDX1BST0ZJTEUAAQEAAAxITGlubwIQAABtbnRyUkdCIFhZWiAHzgACAAkABgAxAABhY3NwTVNGVAAAAABJRUMgc1JHQgAAAAAAAAAAAQAAAAAA9tYAAQAAAADTLUhQICAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABFjcHJ0AAABUAAAADNkZXNjAAABhAAAAGx3dHB0AAAB8AAAABRia3B0AAACBAAAABRyWFlaAAACGAAAABRnWFlaAAACLAAAABRiWFlaAAACQAAAABRkbW5kAAACVAAAAHBkbWRkAAACxAAAAIh2dWVkAAADTAAAAIZ2aWV3AAAD1AAAACRsdW1pAAAD+AAAABRtZWFzAAAEDAAAACR0ZWNoAAAEMAAAAAxyVFJDAAAEPAAACAxnVFJDAAAEPAAACAxiVFJDAAAEPAAACAx0ZXh0AAAAAENvcHlyaWdodCAoYykgMTk5OCBIZXdsZXR0LVBhY2thcmQgQ29tcGFueQAAZGVzYwAAAAAAAAASc1JHQiBJRUM2MTk2Ni0yLjEAAAAAAAAAAAAAABJzUkdCIElFQzYxOTY2LTIuMQAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAWFlaIAAAAAAAAPNRAAEAAAABFsxYWVogAAAAAAAAAAAAAAAAAAAAAFhZWiAAAAAAAABvogAAOPUAAAOQWFlaIAAAAAAAAGKZAAC3hQAAGNpYWVogAAAAAAAAJKAAAA+EAAC2z2Rlc2MAAAAAAAAAFklFQyBodHRwOi8vd3d3LmllYy5jaAAAAAAAAAAAAAAAFklFQyBodHRwOi8vd3d3LmllYy5jaAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABkZXNjAAAAAAAAAC5JRUMgNjE5NjYtMi4xIERlZmF1bHQgUkdCIGNvbG91ciBzcGFjZSAtIHNSR0IAAAAAAAAAAAAAAC5JRUMgNjE5NjYtMi4xIERlZmF1bHQgUkdCIGNvbG91ciBzcGFjZSAtIHNSR0IAAAAAAAAAAAAAAAAAAAAAAAAAAAAAZGVzYwAAAAAAAAAsUmVmZXJlbmNlIFZpZXdpbmcgQ29uZGl0aW9uIGluIElFQzYxOTY2LTIuMQAAAAAAAAAAAAAALFJlZmVyZW5jZSBWaWV3aW5nIENvbmRpdGlvbiBpbiBJRUM2MTk2Ni0yLjEAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAHZpZXcAAAAAABOk/gAUXy4AEM8UAAPtzAAEEwsAA1yeAAAAAVhZWiAAAAAAAEwJVgBQAAAAVx/nbWVhcwAAAAAAAAABAAAAAAAAAAAAAAAAAAAAAAAAAo8AAAACc2lnIAAAAABDUlQgY3VydgAAAAAAAAQAAAAABQAKAA8AFAAZAB4AIwAoAC0AMgA3ADsAQABFAEoATwBUAFkAXgBjAGgAbQByAHcAfACBAIYAiwCQAJUAmgCfAKQAqQCuALIAtwC8AMEAxgDLANAA1QDbAOAA5QDrAPAA9gD7AQEBBwENARMBGQEfASUBKwEyATgBPgFFAUwBUgFZAWABZwFuAXUBfAGDAYsBkgGaAaEBqQGxAbkBwQHJAdEB2QHhAekB8gH6AgMCDAIUAh0CJgIvAjgCQQJLAlQCXQJnAnECegKEAo4CmAKiAqwCtgLBAssC1QLgAusC9QMAAwsDFgMhAy0DOANDA08DWgNmA3IDfgOKA5YDogOuA7oDxwPTA+AD7AP5BAYEEwQgBC0EOwRIBFUEYwRxBH4EjASaBKgEtgTEBNME4QTwBP4FDQUcBSsFOgVJBVgFZwV3BYYFlgWmBbUFxQXVBeUF9gYGBhYGJwY3BkgGWQZqBnsGjAadBq8GwAbRBuMG9QcHBxkHKwc9B08HYQd0B4YHmQesB78H0gflB/gICwgfCDIIRghaCG4IggiWCKoIvgjSCOcI+wkQCSUJOglPCWQJeQmPCaQJugnPCeUJ+woRCicKPQpUCmoKgQqYCq4KxQrcCvMLCwsiCzkLUQtpC4ALmAuwC8gL4Qv5DBIMKgxDDFwMdQyODKcMwAzZDPMNDQ0mDUANWg10DY4NqQ3DDd4N+A4TDi4OSQ5kDn8Omw62DtIO7g8JDyUPQQ9eD3oPlg+zD88P7BAJECYQQxBhEH4QmxC5ENcQ9RETETERTxFtEYwRqhHJEegSBxImEkUSZBKEEqMSwxLjEwMTIxNDE2MTgxOkE8UT5RQGFCcUSRRqFIsUrRTOFPAVEhU0FVYVeBWbFb0V4BYDFiYWSRZsFo8WshbWFvoXHRdBF2UXiReuF9IX9xgbGEAYZRiKGK8Y1Rj6GSAZRRlrGZEZtxndGgQaKhpRGncanhrFGuwbFBs7G2MbihuyG9ocAhwqHFIcexyjHMwc9R0eHUcdcB2ZHcMd7B4WHkAeah6UHr4e6R8THz4faR+UH78f6iAVIEEgbCCYIMQg8CEcIUghdSGhIc4h+yInIlUigiKvIt0jCiM4I2YjlCPCI/AkHyRNJHwkqyTaJQklOCVoJZclxyX3JicmVyaHJrcm6CcYJ0kneierJ9woDSg/KHEooijUKQYpOClrKZ0p0CoCKjUqaCqbKs8rAis2K2krnSvRLAUsOSxuLKIs1y0MLUEtdi2rLeEuFi5MLoIuty7uLyQvWi+RL8cv/jA1MGwwpDDbMRIxSjGCMbox8jIqMmMymzLUMw0zRjN/M7gz8TQrNGU0njTYNRM1TTWHNcI1/TY3NnI2rjbpNyQ3YDecN9c4FDhQOIw4yDkFOUI5fzm8Ofk6Njp0OrI67zstO2s7qjvoPCc8ZTykPOM9Ij1hPaE94D4gPmA+oD7gPyE/YT+iP+JAI0BkQKZA50EpQWpBrEHuQjBCckK1QvdDOkN9Q8BEA0RHRIpEzkUSRVVFmkXeRiJGZ0arRvBHNUd7R8BIBUhLSJFI10kdSWNJqUnwSjdKfUrESwxLU0uaS+JMKkxyTLpNAk1KTZNN3E4lTm5Ot08AT0lPk0/dUCdQcVC7UQZRUFGbUeZSMVJ8UsdTE1NfU6pT9lRCVI9U21UoVXVVwlYPVlxWqVb3V0RXklfgWC9YfVjLWRpZaVm4WgdaVlqmWvVbRVuVW+VcNVyGXNZdJ114XcleGl5sXr1fD19hX7NgBWBXYKpg/GFPYaJh9WJJYpxi8GNDY5dj62RAZJRk6WU9ZZJl52Y9ZpJm6Gc9Z5Nn6Wg/aJZo7GlDaZpp8WpIap9q92tPa6dr/2xXbK9tCG1gbbluEm5rbsRvHm94b9FwK3CGcOBxOnGVcfByS3KmcwFzXXO4dBR0cHTMdSh1hXXhdj52m3b4d1Z3s3gReG54zHkqeYl553pGeqV7BHtje8J8IXyBfOF9QX2hfgF+Yn7CfyN/hH/lgEeAqIEKgWuBzYIwgpKC9INXg7qEHYSAhOOFR4Wrhg6GcobXhzuHn4gEiGmIzokziZmJ/opkisqLMIuWi/yMY4zKjTGNmI3/jmaOzo82j56QBpBukNaRP5GokhGSepLjk02TtpQglIqU9JVflcmWNJaflwqXdZfgmEyYuJkkmZCZ/JpomtWbQpuvnByciZz3nWSd0p5Anq6fHZ+Ln/qgaaDYoUehtqImopajBqN2o+akVqTHpTilqaYapoum/adup+CoUqjEqTepqaocqo+rAqt1q+msXKzQrUStuK4trqGvFq+LsACwdbDqsWCx1rJLssKzOLOutCW0nLUTtYq2AbZ5tvC3aLfguFm40blKucK6O7q1uy67p7whvJu9Fb2Pvgq+hL7/v3q/9cBwwOzBZ8Hjwl/C28NYw9TEUcTOxUvFyMZGxsPHQce/yD3IvMk6ybnKOMq3yzbLtsw1zLXNNc21zjbOts83z7jQOdC60TzRvtI/0sHTRNPG1EnUy9VO1dHWVdbY11zX4Nhk2OjZbNnx2nba+9uA3AXcit0Q3ZbeHN6i3ynfr+A24L3hROHM4lPi2+Nj4+vkc+T85YTmDeaW5x/nqegy6LzpRunQ6lvq5etw6/vshu0R7ZzuKO6070DvzPBY8OXxcvH/8ozzGfOn9DT0wvVQ9d72bfb794r4Gfio+Tj5x/pX+uf7d/wH/Jj9Kf26/kv+3P9t////7QA4UGhvdG9zaG9wIDMuMAA4QklNBAQAAAAAABwcAVoAAxslRxwCAAACAAIcAgUACElNR18yNjMy/9sAQwADAgIDAgIDAwMDBAMDBAUIBQUEBAUKBwcGCAwKDAwLCgsLDQ4SEA0OEQ4LCxAWEBETFBUVFQwPFxgWFBgSFBUU/9sAQwEDBAQFBAUJBQUJFA0LDRQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQUFBQU/8AAEQgAoADwAwERAAIRAQMRAf/EAB0AAAEEAwEBAAAAAAAAAAAAAAEAAgQGAwUHCAn/xAA6EAABAwMDAgUBBgQGAgMAAAABAgMEAAURBhIhMUEHEyJRYXEIFDJCgZEVI6GxM1LB0eHwFmI0Q1P/xAAbAQACAwEBAQAAAAAAAAAAAAAAAQIDBAUGB//EADMRAAIBAgQDBgYCAgMBAAAAAAABAgMRBBIhMUFRYQUTInGB8DKRobHR4RTBIzNCUvFi/9oADAMBAAIRAxEAPwD5bpaUrICSTUbpCSEppSThSSk/NO6YrCxQKwD19qkRDjkUAHrwBigA4oEHFAhUCHDp/pQBMgzXIUqPIbIS8w4lxpakhW1STkZB6jPY1HZ3QpRjOLjLZntTQXjvpvWaLW07db87KYi+bI0p9xSmMsgDz1qm+ZvWhCUJUFEZSMkhWCT0KVSMll+h4bFdnVsPJzyq19JX1XLTbXZ8zqLek2fEu1oiafuDb0BE5E6TZ5wZlvEYKlsv5SoONKKlYWMZ9JwTitOdrfVe9zl5VTm3ltLVcV6xtbXozxj44eA+ofDi83a8psIgaYdmuFhuPIMhMFpSsoacUQCQnds3cjKSCcjnBVoteJbHs+z+0adeMacpeO3Hj75aHIy0FIK0ZwPxJPb/AHFZb62Z3LJq6MeOKkIRFAC+KB2GqpiBTEKkOwaYBTSAeCcgjg54IpMdi+x/Fh5llsOWuO4+hISXtxBVjuRQm4pJHGn2ZGcm1KyZXL/rG4X98l1YbSfyN9Ki0pO7NtDB06CstTSNK3Kxgr74zSZvWmxlYbVIcDbSVLWeiUjJ/QVCTsrsmk27Is1r8P8AUN4SRDssyQD7NYB/esFTGUKfxTRuhgsRU+Gm2azV3h5f9GtR5N1tMmBFkKKG3XE+kqAyU5HQ45we30q6hi6OIbjTldohXwdfDRUqsbJjtN25q6P7fNS0SoD1DuaVebprY1YHDLEytmsbq8aKdbbbcDqHUqKgAPcdazU8UuKOnieyKkEpKV/0YmtEPTYwWlxsKHRJHb9Ki8ZGErFMOyqlWGZNFQuENcGUtpYwpJxXXpzU45kefrU3Sm4y3Iyef1qwzmzjWl2QgFOBnuaolUUWaFSlJaGQ2F7BIAwDjBPWl30R9xMYi1qUM56f5afeIr7psjuw1tZ9qtUkytxsYQgk9RmpENy0WfSRntpOFKJGeDiufVxKps6tHBd4rkW62IwU7XG8bTjaRn9asp1VU1RRUoulobnQfiRffDef59pmuMtrAStpKzgjPb/uR2IrZTqyg7xOVisHSxKy1F6n0Y8GPGTS/j5aQny4z97DQW7a34u5IDZQNzhUFBWFbT7jIwODW2NVJXWx4nEYKpRnabd3s+fr7Z568TvsdPyNO3PUOnrVN01eoUl4yrM6Au3ltKuFMublFG4eoJO5Kt4TwUk1GdGFRXhv7+R2MH2lWoSjTxDzR2vxX5/vqeT5+n5ceUqOqI9FmoALsKQ2W3EZ6YSrB/TqPmuc5Ok8tXT3x/J6zLGos9Jp++H43NU80th1TbqFNOJ6ocSUqH6GrU1JXiyl32Y0/wBaYcBihTEDvTBCoEFNAwigY5J4J7/SkPzCKQwtAbiQQCTgE1FkkTbTCEuchCUEJWvaOe2elU1Z5Ytl1KGeaXM9oeAPgrZprDL0pllLygDlxI5HevnPaGPquTSeh9KwGBpU4J21PXVn0HYIsFoIaa3spCf5Sc4H9zXm5VZS4ndSS0RrNfaK0jrnTVw01eoiJFsnMlC1t4Cm149DiDj0rQcKB/0JFOjXq4erGrSdpL38mVVqVOvTlSqK8WfHWBPdt8hDzKtq0nP1r7bOCqJxZ8fo1p0JqpDdFnGuFuxAwtBThRUFA9Cetc/+HZ3TO7LtiU6eRx9szwddIiMFryVHA4Uk9apqYGU3e5Kj2vGlHI4lWuU1VwlreVwVHNdSlTVOKijgV6rr1HN8SMnqD2q4zG3h3UR0AbSTjtWeVLMy+NXKWOyNSNSLW3FR5YaSFOuOOBttsE4ypR6ZPbqccCq4YacnaKuKv2hSoRTm2r7JatmzetVsiy2ors6TcJchQQlqHH8lClccBxzk/XaK0xwsItKTu+n7ObLtGtUi5Qgopc3d/JfkZMtFsZSt026OlvGWnplwLjawRyQlOCo/oB9a1xpQgtvmznPFVqrspO/FKNn9b2IaGIKHBi22CSlJGCmYtBx8jPPT+/vUmo20S+YRlVus05r0TLbBvci3xVJRYbYtvHpXGcCv0zuyOtcWvg5VJXUFY9JQ7RhThllWafVfoqup9S26Y+GXoD0ZxAwsodJxn/1NW0aEaaalG3qRrYipVtKE1JeW5WZNucQyt1pbcuIk8uMuAlPyU9R/atfdvdMyLERk1GStf3v7uWHws1/M8MtbWy/xpc5hplwCT/D1pSt9ru2d4KeeBkjjtVW+mxOrTzws0n7+j96n0o8KvEVfj/pZyVbnnUGfFcjqZVgYcSkpKFHgnaSnHOQFHqCKvpNN2Xxffr71R52vRlRnep8F+W3G3vRnB/Ev7M2mGZzl3sUpUludEcfTa5BU4q3pWErHqUrkhalYBzj1cK9OOZjcTRhlp03ecldW1a89Vb7+d7HpMJiK0IyliF4Iuz4Jv5O/DTZq2qau+Z37RelBpCZEebLqm2vNbm7AhaVYwCO4Gexz3+K5MJ1qU1LNvw4EXXVaVlGx5pWkoUpBxkEg16dO6uPYxmmIFMQutAw4IFADk8jpUSaCAcYpggpTk44/U4qNxpAJ2jPQZyaBm40y8Bd4pxnavOc9ayYheBmrDv8AyxZ7A8M9bqs8SOkrSAEjjPP1r55iaGaTPo2HrZYo7FH8VHHAlzzdqtowpPU1y3h9Tf31zW3zXS5YLweU27jlbZ4I+RTjRIyq6HzJHTrX2E+RD0igGOoIhoBMyNJyrjmgfkdH0h4atqsn/keoQpi1KChFYKthkqCSdyiDuS30xjlWeMDmtlKisveVNvuefxnaElU/jYXWfF8v39jeWtiXemBY9B6eCVyHQ+7cFu7fMLaSFFIWTtSEnJ289cdasV5eGjHcxVMlJ99j6t7LRW118jONL3CwMPKudzkOF5shfnuMx0Aq3AlO8qczx+LAPNTUXDWTb+n3KXXhXajSglbzb9baehXH9IW9Db7ke122QzHKQtz+MZwSOEjJTuP09qocI6tR26m+OJqtpTqNN/8AwaaZaLZGdCHbfPhqH4lR3UvpH05P96pcY7NNfU3Qq1WrxmmuqsRXLfZV58m8PNLyfQ/FII/Y1FQh/wBvoS73ELeCa6Me4ZcVAS66LrbQkhXkkK4+SRkH61O0o9URThN3tln192IMZ6M3LakxD92wpLbjLqtxUDwcHuPg1XdLVGq02nCevW39DoqmFyXGzHSgoKtzQVkKQM7sH/MBzjvjjoBWOrGa4/p/g6dGpB2dtP64s9T/AGfNY3bw40m9DswjIclSly0SJiN/kKQkABCCoHOAeox8GuPLH1E1GlHxp6t7eluPPgyzEYOhLNUrS8DSsl8Vut1txXFdD1DpRp7xE0y3EticwHlKMl9QJWVn1LUpQ/McnJ7/ABU8NhYUU5Sd5Sd2+p5jGYqpXnorRWluS5eR57+1fC0boe2O2SxKU9c1KS/MeLm4MpAyUkgkeokYHWoKMpVLvyRowbctPVniVa9yio/iJya9ElZWOiYzTEIjA60wBnp7UAIHNMDIjOOP7VEkEcA+xFLcYACDyeKBoc4jjaDlR5A96iOxM0+opubJz0NUV1eDL6H+xHeLDdktx2go447V5GrTu3Y9lTqWSLZB1UG0bCvKex7isMqJsVWxmc1apSfSsZ71HuQdY8cg19GPnQ9JCeCc/SgTMiQDQQ1HKTgA0CjK7sdJ8FtGRtR3h+RcYoehRRkmX/LiKV0UFuHgqSCFBA5Vg544OzC04zk5zWi+Xr+Di9r4qpQpqnRlaUuXxei89G+B169+H8jUuoZ921vJW1Zre22I1ut7iAp5jy8tvJBG3ylEAbeMc5rU4d5JyqvRbJHmqOLjhqUaODXilq2+D5eZznVfjE7GecgafeS1AbBQ3IjI8paklG1SVL/EofsB2rLUrteGB2sL2bGa72uvE+D1/wDDl8i6SJSiVvKH0HWsl29Wd+MIxVkiMte7kqKvnvS1LEPhSHo7mY7/AJavhW3NF8uo5QU1Zm+i6j3KLN3ipuMdSSlaVel1PspKuoNWxqX+JXRzqmGa/wBTyte9iOuKuEwLhapLhQj/ABUf/Y18n3T81JKyz02PNml3NdeT4P8AZHedZnJMnYGJDRSt0IGEODOMgdjUG1JXe5ojGVKSgndP6fowblwZ6nkY81t0OISeQcHd/tVNSOe8Xs/7NlJ5IqXvTc71Z7xp6Y1GmRrnhl0BbjDaCXGl/mSQBgEHP/Ga4cKUqXgkiOIqd5JvU6zqD7Sdv0ToZNk07OfjtqO52U8ny8nHZP4nD8Hj4NXxUnJ21fL98DmTw86to7L5f+nkXW2t39WS1Eb24gVvAcOVuK/zL/0Hb5Nb6NHI80tZfbyNtOCpRyxKuTWotQinHWnsG434oABpoByenXFIaHoVnjgdTRYdxJUc4B+tRY0ZEOIwAo98Hio6kxspO1WNxJxyT/pQhMnWXD8xKsgOpGTn8w/3rPW0j0NFLxS6nR4U0pbSAr8orhTirnoIS0J7dzWgD1dPaqnBFymwi6KJ5V9DUciHnOFDFetPIBFAGRvj6UCZPt0N6fLjx47K5Ep9xLTDLSdy3FqIAAHc5PA96VnJqKIeGnF1JOySv+z2z4QeHEOz6PTNvzMdEKwqcfixJDagwuQ4vaC8Sc7+AFJxj0J7HFd+nFQgoyW3D89eZ82x9edWtJ027y0v0Svp0e69Tgnjh463zX6zp7eiNZojpCmGhy6scYUs8lCSDhPQc/FcvEVnUduCPT9mdmQw0e8/5S58P2cddXuV7DoKyJWPQaIaO1MQFD2/5oBE20WS4Xxx5MCE9LLKC68ppPoaQASVLV0QMA8kipxhKXwojVq06KTqSSv705lim+HWqrZGC5NgmOxCVJTJipElvKU7lgLaKgQByeePipSoVI629+hkp43DVfCpr106cbcTQQLk7bpAW0sj8pT1Ch3SR7fFVxk4vMjVOlGrHJJGWWf4dK8yNuQ1IRvSOoxnlP6Gm7qzQQSqJwmrtAkvJmoTJSxsUn8flg7Bj47cf60pXk7llNKmst+OhAW2UOKCQcZ4IPOO1RTvqWONnYYoHeSeVe561JPkRtbcacZoCwBTDQVAxY70CsDGcUxWEelBISRigEhwTtBPGaiSSG5wPegByXAvCFdPyqJ6f8f2qLVtUPoTrUCxNBOQR1HcVRV1iX0tJFxZk4GcgEDpXKcbnYTsMlahZiDB9R+tEaDkRliIwNedZN78bCMVd/FZR/MXIo2K65yg/WgQ9sHr2oFa5evDyHc4mpoE+2hAks/zWluICk5CSFJAP5iM4PbiqFXdJqUdzPioQnSlCptoeqvDefMntybtJuTDq5EluUu0S2ipkLQlJ83JyCohJGOnFU1e0cQk6kLXb2tfh+jy88JRjONBp2imk7663/JyT7Q+hrPZkNTbBbY0GKyt1uQpnO5a1rCiTnkgerGegVgcCq6WMlWl3dSKTT3XE7OBur+NyTStfhb9HnxSufiuidMclWaZGxbo9vtWm9N2u7zDHu1yuQccZtziVbIzSVqRvcGcLUojIHQAA+rOKvSjCKk9W+Bz3KrWrSpRvGMbXfFt2dlytx59Bt91427FiwdOwv8Ax+3eQky4zZCw/IIHmLJVk7MgbUk+kfPNE6mloacyVLCNSc67zu+j5Lh682ae26gmWp9l5h1bS21bgphXlLH+YJUOmRx0PyDVMZOLujTUpRqK0jp+pNIq8X7heNaadkMM2hDqPv8A/E9kRcNwjCRjOHs4zlJKiTkgZrVUpd83UpvTjfS3voczDYj+DCOFrxefhbW6/q3VFTm+HupIlqmF+0S3YsKaI7cllpSkqJSo7kJICigpTu3Yx0zyQKqdKeV2V7M3RxFLvYtuzkvtze1zoHhn9mi96juYYus1/TjpgLnrgvwVpkFGdqRtXtBBBCj3AKeOcgjTknrdO3Iy4nHQhFOCUlmte9+HTXp8yv8AiN4C3nQjgfQ8xc7Et1DDNx2+WpW5RSklGVEYwCcE8KHXNZna7sb6WI7yEO8jZv14c9Co6s0NeNJrYRdIiW23shmQwsONqP8AlB68jkA4P9qinbZmuE41lZLUrTra2vxYz8HOKsTT2CUXHcxc1Ir1CATQSVxDr0zQCCKAAr6UIeoscZzQATxzx9KQxhH60AMIOff6UAbO0L81QSTlQ/Cfce3/AH/as1VWRppasn3W7fdR5aOVdKz06WZ3Zoq1XHRGkMkqJCiSk81qypGO9xmCVpz+YUxEdp3ZlJ9SFfiT7/8ANXFbVzI/FUw2y5uBbdBU2QoZxnHI7cgj9DTatqRjK91xQGRuUBz6jjjrzUHpqTR6E8MURY+q7VAcAbjhYSlfUBSuQfnk/wBa5kI95qczHScU301PXts0t90iJgxbU2t9CisrQn8RPIz7cHpTqUoRhqzzlOtOdVM4B47T4aW7lAUwGHyFuOpBBCSU+n9/9ayRhZxmuB1cNfM7bM8flOByoCu+d0uWltMMM6UuWrbtE+92iK+mCwyXVID8lSdwT6RkgADPI6k5OMVopwWV1JLRfc5eJrydeGFpO0nq3vaK/PAr12vLl4MYKYZjtRmvJabZBO1Gc4KiSTgk4z0zVMpXsuRupUu7vrdvXU1wBqJcPGSMD+9AjJ5jnkFguKLBUFlrcSjcBjO3pnHGetO7tYVlfNxPUn2b9Qw9XOae08ZEqbPtbDkrzJ5U2iKdxU6sOeZhbaUJThOArOMYxXUw7hUyw1ujyvaEKtDvK1koydtLar838zvWqrdp2E7Muj7650mBzFuBcckLeS4tSnyXFLUFoWhIbzwUnP4SRUMX3TV2rtcd/Pf5EMFTxGkF4VJ6qyitNtFbVN3V/qrkCzItTwataWLrdpFxWppLDmW20MPAbnNuApQSsYzxgIAPQmuUmopRknf5aM7MqeZXi1b56r82+pN1n4I2TVeg39PXC/sfx1G9pTpiqb8jaraCsn1HYQFgpGDu6+0lQjBWcvEny4dSVKtUnUz04Wi7Na/bpe630PFfiz4RyvDG5Qoz09m5sTQ4GpLICUlxGN6MgkEHOQrPXI7GoSj3autl7ud6L73R7vbz4r3zXM5u435auc47EjH/AE1YpJozuLixg60MEgkYJpiYAcdaYCJpDsIcAntQNC6g/FIYxRpiMajmkCNjaTtdUeuBms1XY0UtGRpL33h1aj71NLKrEG8zuYc7cDND1EOSohCffNIZFFXlZtY7TU62spWSl5p1TSNmPUkpKgD3/FwD7E+1WaOJnbcZu3FXMbsNEWPBdD2955BdW0E/4Y3YTz3yBn4qEklFa7k4ScpSVtEehPAbWOn4OoYF4uLzaHY6PSl5sLQHQAArB+M8ViUMisjgdp97K8UtzqOvvHW8uXBK5OomDCT6lCBtQnJOQEBPXAx1+ayzblol76spw+FUOJ5+8ZvF3/zOQ2hpCCtCChT4BC3jxyrnjAA/apYbDuDvJnap03fM1Y5PBhSblJbjRGHpT7mQhphBWtWBk4A68Amusk5OyRplOMI5ptJdTd3qa/AsdssadzCE75ctgK/G+pRCSsA8lLYSAD+HKiACo1Ju0VEz0oxnUlV34Ly42838/Q0QBPQ/14qo2WFkjt/WmREPV04pgb7TOkJmoEvS/wD4tojKIlXJ3b5bOEFZABUN6sJJCU5Jq6FKVTxcFuzJWxMKNo7yey5++bOnKvkLw70XC/hsm5uWi8xlx3EgsRXpalBXmuqCFuKCRhpKQojHqwDnNaHlpwW+V+Svz59PqcxKriK0r2U42a3aWunK/Fj/AA/8Vb3KsULTbcANxW2lR2Rb3funnvrStCS4setRUXDlIUAslPHFZIVUl3bWny4cfmdOphM03iM3i+fHh8jvmqtGXaTpuPZ417nS9cO2tph6VGkKK0PoH81ClAJAAy4jaPyfjyVYHL7UqUaE1TXidrSV9lrv53atvtcs7MnNJ1Ussb5ou3HTblqk7vTc3lyuVzfct7t5eTcH4rcZhx9ltwqCUJPlDIVnIJUo8nk89648cbXlVdV2eyW629dTowo0IUu7imt3a6e+/DyPO3irZU3+7vT3Vh50KU1Mlsx0oacwTsWpIASlYSrG5OOn1z0o1ZTedxS8r+unJ8vUSqyjHJe+29vJa23XPe2hzO9aXLtqmXFhDgTGSgv7xkFXA3BXuR1GO2feraVa04wfHb39i6o+8Tlx3fnt9eJTx1rpGIKhQgY3p1FMAnOOlINQAZoGg9Dj3NAzGrv3oAxk0CJMWUGAsEZJFVSjmLYyykcOFKtw/apMiOUnIHzUCQOQMe1IDAKtIGws0lDEhaXSlKHkFvzFDPlK6oX8YPf2zVkGr6lFWLcbx3X16epvNZ2pEIwJbKAiJKbCY+3lIShI3EKzzlSlH4GKnWio7bGfCVHO6e639f0V5iU9GWVNLW2T1KFdfqO9UWubHFS3Rmdukt4bVPkj2zio5UJQitkRilRySP69akOxvLC0+i23SRGbcXKWG4jIRkqyslSsAc5wgfpmrYXs2jHXs5wjLbVv02+rGQdLS1yw1JbVCZS2XnHVI3hLYIBIweTkgYz1PJFLI72ehN4iGW8Hme3qbL+HaSS235l4uPmJUQ4ERErKkhSeU4OBuBVwTwU981ZlpcWzO6mMb0hG3n728tSRGa0RcFBku3i3uOEpDrpbdQ0R+E9t27vynbjjOeJpUJOyuiuTx9PVqMkvNX/FuHMmao8I59pYRMs86Lqe3lgPrXbv8dgfm8xgkrABz6xlJHOalUw06esXmXT8EKHaVOq8tWLg7212+e3z1I8CzXOPotf8W3QtPSm3ZkFboQQZISNpSOVDzNoRjjI56JpRjPu7T0i9fXgTnVpd+nS1qKye+3HppuSZzc3Wuh7H9zW7cptqQ5FchMRAFRmUDcheUjCkqCjyfUVAjnil/spRgtWr6WJJqjiZymrKSWt93e1un2LNZbLFhSoctqKY4aUxJR5bhUC6nne2o8p6Dgc5B54rzdTEd63k09/Q7NR91BQbu7v0Vl8/2d509riFEvCZMCTJkYVvaZUcOJK8709MdyPnNYe5aslwdzMq6cnKXHQ6Xpy2XzWUGQmwWlaXHmVMb0ANlQ68kq2gjPX9qTppPVkoVFyOU668NLzZdPqt0u3OISofeX5BUnCUDj1EEhIzxk9a7EaXhTbMssRwijgfiLqCHa9PrssN5EiRKc3ulsehCR1579AB7DPvRTouVRTeyNFKTUXJcTlnJNdIkJRJFPYeoAOKBBPOaRJiGQKBoFIBhPHHemBjPJoAc0hSnAEpKs8YAzUWxpajCnAwcgjgg0AOTkjHTFQZMckeqo3GiMKtKxwOD9KALDp/VCrfEVbJrCZ9occC1RnMZbUcArbUQdpIGCO/1wasUrLK9jLUoKUu8i7SXvU3sbw9jarLjmnXXgoIcWmJISFqdIBUEtqHUkY4PIzk1ZGnn+EySxUqOlX8fM1Lnh3qNq3yJxtji40dYbcKVJKgTnHpzkjjqPce9Q7udm7bGj+VRzKObVkIabkMIcXPcZtoSpCQmRnconnACQcYTzzio5dNSx1VmtHU3JuUOHpS4ItPmgffkpWXjuIQpBCVA4HB2HqON2KsvaLtzMnduVePef8AX63/AGTNEOyyZTNzjSZlqejCT9z+7lxUryzlCUcgpRuwVEdUpIp0r3d1dfchi1C0XBpSTte9rX+/TqPt8bSF5iMTHZqIMyNEfflW5THlokLSr0IbVnBJSR6fSeCMmpLu52k3boEniaLcEnJNpJ32vu3oSNP6+sxLsa+6Yt10hLdbWpYipYcZSDjalxvG1Jzj9jUoVl/zgmvKxVWwU9HQqyi+rbv6Pdki1Lk6N8TSixSFRISpio7Uh87VtMFXrSpSe23IIT1BAHWnd0q3g014/oHGOJwb77xO2tuL4Oz68+I/xavtxhSVaVkTrg5CjOB5tEpZALZBU0MfmA3lSewBAFPEVJawTdt/x74EOz6NOSVeyva2nNaP98yjWWfItM9p1mW/GaLiC8WFqSSgKBOQCMgDmsilZrgdedNSi9E9zt+otWQLG4WLalnU1j2JDM6I4HFs5OPLdSlPTcfTxnGKwYjAKM3PDvR8PfPkZYVZV42q+GS9/q5s9EePOmtL22YlenUzbi+cCSp8KDSR2AOCDnOc+wrn1KNVaX+hfGjd3/s1WqftLXO+ONhS2o0ZrGyO04pKAR3KUbQTUYYao3fU25YpJWSKtrL7Q2pNVWkWx+4yHoQAAj7ilo46bhnK8dgTXQo4dwd3oRqZZ76/Y5W++5JeU66suOLOSpXet6VtEVjc4NTAbkZxQAehFLdDEaBh3ZGM8UAI8AVEkY10xDQnn5oYHdPs+6OZ1A2645HDygrjI+e57D/evK9rV5Qkopnq+yKEZxcmrnNvEuxIsWploDjSkPoDmGvy/B+emfnNdjBVXVo7bHI7QodzXa5lUHoUSFA/rzW3VnPAtW1XHI/vQkBhFWESQwjzWXx/+aQ6OPYgH+h/pRuiD0aMQpEjLGlvQpDUiO6tl5lYcbcQcKQocgg9jwKknYhKKkmnqmWlWvX5c9m5rV9xvSVBS7hDSUKcOMZKPwknqeBk1c6rbzceZg/iRjF00rw5Ph/fkb24eLjNztT1vftTTnnr8x2UBgFWQSoMcJB9I7/5veh1bpplMMD3clNPbhr99/aM2nvEewwrVPtMq1Rm4khJWJLUBAfLxUnaVKycISN52j4xzRGa2e3kiFbCVZNVI/Ev/p2t/bZPd17FtLLzMqRLESS4lz7lBioZS8kEfzC4chWdgBAIByehGKuVTLpd26WM6wjqyzRim1xbbt0te6M8zxTtN7vSX5Wj42pml+W7NW20plalhP8ALbRhJCUoKlJyr1L5yRxQ66k7uNwh2fOlC0auR621+b158uBY7pqPRlkgv/cbAm2u3bymf4JHmeY6kH1qWrKVJU2VqISknOFbQBjNXSnCn8K34GClQxOIdqk75Nparbl1t+S+2KJpqfcpTV4lyJrBCDb0yWsR4ykKAUtBRkoKCMLSnrtUSCK1KUWnF/8Ankc+XeJJ09HxfF+fnwNleLj4cas1FO0pr21OeHl8uDQS1PQtMmBKbXhLT/mhAU2QEenckJA/Fg0pVKTbp1la/ElTo4qlCNfCyzKO6ejXF6fexM119iS13eJCOlVKsMhj+UudIQ6uI+AD6lHJ9RxncjjnpjmoVsLTt4NOv5Rbg+168ZPvbyvd22t5Pl0Z5z1r4b3vwR1YuPdoa5cVLiUsXKOt1mK+ogKG1Y6nHBGffmsNSlPCyu9VzPS4XF0u0YWg8suKdm+RzOc89JmyHpI/nuuKcc46knJrO5ObcnxOgo5EoLgYCf0pDsHODQFhZ596BgJzTAQxTuFh+OBUbkrAPSmIWOBSGhZ6Uh2GLPPehAAHFJ6gXGx+KV10xpx2021QjpdJ8xwdVZ965tXA061XvJnUo4+rh6TpU9L8Sozpsi4PGRKeW+8rqtxWTW6MYwVoqyOfKUpvNJ3ZhcOSFdMipIQVBJaSRnI4V/396jd31HpYxCpkSaw0tu2SXydqVqSwP/bkKOPpgfvU0tGyltOaj6kQH+9QLRZ5pgL2oEEGgQQaYiezdVNxWmFR474ZUVNF5JVsycnjO08+4NSzaWsUumnJyTav78/kKRd5coAFwNIAI2R0hpJyOSUpwCT3NDk2EaUI/vX7kYKBHZBx1HFQ2L277ndNN6yguaLmXxV4Ykagt7SAzbZnmIdaIB3vBzG0qXgAKB3EHbxya3wqJxcr6r3c8xWwso140stoye6434eS/Z1zwA8Q0a605Jtd4KJNxKW1OSYrDAdg4WptLrm473cBQ5VlJC0pwc1fQqucLN3Zi7QwsaNXNBOK+afpwPVWlJn8WtESBISqFGLjbogNlAC9oIBTtykFRHKfcHNaE2nroziuC3hrb39Tz5rq+TNU+J94s190jIf8PGXw2w48yQwtZHK0kpO0jCsIScjnrwKcZPM6bj4eHK/vY2wVOFJVVU/ya35pLj16nAPtM+EsDTWpP4xpdpAsE1hLwiMtKR9zUlICkYPXPXqcEHJrm42EKU4yhopaW5Hpuy8VKvTnTqu8o63vumcGJ/ash2QZoAWfimAs0xCzQMyLKeNuaiiTGE0xBB4+KXEYs0hoYo0wGZxSAbnmkMPVJ9qQAB9IB7HNIkJTnAxwO9LzAkW+3ruLpSFBllA3Ovr/AAtJ9z/t1NWRjmZVOaguvLmOuU1Mt5KWkluK2NrLZ/KnPJx7k8mnJpvTYUIZVru9yKO1QLAH3pgH9896BBHWmAhSAINMQ4HigQs/rTFY2MEpk216F5iUvLkNOIS4ralQAWOp4BBV/WnutCqTampNaWf9ErT+opunrgzMt8+RbZjXpRJjr2LSM5wT3GQOD7CqrzpvNDcvlCnWjkqK66nqjwL+0jeLM1Es+q7vIukWZISmI+/jdGSEYCcpGSVKxjrz71dRxUqukmcHG4CGHl/jhZWvp7+nyPUsjVyHW4USXHm3G0stpeSw1gAI3Yz1wCFqxnsT2Fd+jUjGm1J+/wAdeB5GvRnUqKUVpt69OvTjwuzyp4xaxn6q1vJiCG5FsrEhSYxkseW6pPRXmICiB0xxjOMkV5jtPFKs+7g9F7v0PXdl0O4p5qivJ8f66r7bHlrV8Nm3aluMeONrCHlbE5ztGemfiijLPTTZ2orQ09XEhxUSeee1MLAzyaYwigQc0hgPFFwCDSJAJoGMUaQhuaAAeD7UhgzzSGCgAZoGWrXTEa2SmbfbXGxbQPOS02/5qt+Skl04A3ccAcAcc8k6a2WNow2Ofhc0051Pi224dOhVj1rMbwjqKAFQINMGGgQh14oAI4FMQfy0gF7U7isZI73kOoXsSvac7V5wf2piaugyHfvD7zm3YFrK9uc4yemaTYJWVjo/h8tN0Zg+TM+7z4bgH4gFYycKGfYVzZwlCo5J2uKpX7uOVq65bo9TWFF0KotvTLlLb2KJUo4b9aTuz2wSlOe1UynWqWjKo7P+9DjNU43lGnqunLVf3qVbxK17a9AabuFtdER+/PALaZZw6iL3Clr9wCQEjrxUVhUpXirInRqVarUW78/fM8eTZSpst19ZKlOKKiVdTXYisqSOwloYQamAQcUAKmAR0pgE9qXEYDg0AGgAVEkMUaYhppDBSBCNIY2gAUhkmXJcmSnX3l+Y64oqUoAAE/QcAfAqcm222QiklZGHFRJCFACoEGgBUwCBzQIPegBHmgAgUAIUAH5oALTy469zatppNXE0nubhGs7qiOWfvbhaPBQXV7f2zioZFuVOjB8DWS7k/O/xXMpHRCRhI/SppWJxpxjsiOakSFQDDTAVMQ4YpMaDQMBxRcNAg0AgKNIZjNAAxSAFIYCeaBgNAAFIDcrgLvIQ5DZ8ydlQkR2h7HhaU+xHXHAxngVoyur8K1MimqOk34eDf2/Bq3EKaWttaFIWklKkrGCkjggjtVL00ZpTurobjnpSGKgQRQARQFggCmAuKBBoCwqAsKgA8Y/1oCw00ANoAOOaAEOKYrBFACqQhwpXCwu9MAnpSJMHSncVhA8UDArrSAbn4oCwD3pDBSGNIpAA0DFSA6C9Y4pbst8tyQoEo+8R0OgYAykqPTZ0IIPX9a3qK8NSJxVWknUw9T0f19SuXfTlxjtm4KxNjvLJMlj1eo+r1D8pOc+3X2qmdKds+6Zup16bfd7NcGaYHJFZjYDHtzTEEH2oAIoAIIoAVACzQAhQAcigQ0q4pgDOaABmgA0AKgQe1MBUAEUwFTEHPFJjQqAF2oGNPJo4ANpALrSGCkAj1oGNNIY2kB2q22+zQLJd4Siw8y68h5px5xSGnQkHhsZ9W8nHPA55712MkI5ktt+h5ipOrOdOet1dO2/rysRkthDUtp99pCNyJEdTK0thB2qxnnvyn2ODmi9k7+aJy1yuK12fHzKXftMFG2Y0ppqKVBLrwICEE8A4BJ5IPT9KxTp38S2OlRr/APB78OZX5UCRDUkPNKQSSBjnOPpWdxa3NqnGWzMIwef6iolg5Le7oRzxg8Ur2AbUhC7UAIdaAETjNAgE5pgDrQIXegYqQCpgIGgA5oEKmA7PFAAzTELNDGHPFAAJ4pDAT0oAGaQwUgBnPegYM0gEelIYAMmgDqWg5aNU6bft9xbbfELaw24E/wA5LSwQAkDkkKHU5688V06DVSGWfA4WLToTU6fHXpdfkemcl6JetOznC05DKDEekI37VJxgHb2UMgdcZpXunSlw2uN6Za8Nb72K2qaxapLkW6xnQF4SplHCUBOU4Ge3HSsreXSRsiu8SlTftlri2SRKW49aJ0ZTchtwsBs+pHGDjnPHI+meKmpPNdGWpkStNbGRzwymauuL78aMp15aENuDKUKaWE8qVgjg+5HP1q1U++eu5R/L/jLLfRa89CmXrR8jStzEWY7HecCQSqKvegZ+eOawVI5Hlvc60K0aqvFNeZpbuwGZRUgAJWM4HY96hB+GzLoyvdEEc9qsJh6UAA0ANoEInFACHNAxZoELNACoAIpgHPSgBZpiFnmgBZoAGaBhzxQA3vSGI0AA0gBQMOM0hjTmgBDPakB//9k=").build();
        List<Image> images = new ArrayList<Image>();
        images.add(im2);
        images.add(im3);


        Section heroSection = Section.builder().images(images).build();
        List<Section> sections = new ArrayList<Section>();
        sections.add(heroSection);

        MessageCard messageCard = MessageCard.builder()
                .type("MessageCard")
                .context("http://schema.org/extensions")
                .title("I am a title")
                .themeColor("ffff00")
                .text("@ben.woodward@infor.com I am some message text **with** bold and _italics_" + "<br>" +
                        "Will this make a new line?" + "<br>" +
                        "![Tux, the Linux mascot](https://d33wubrfki0l68.cloudfront.net/e7ed9fe4bafe46e275c807d63591f85f9ab246ba/e2d28/assets/images/tux.png)" + "<br>" +
                        "My favorite search engine is [Duck Duck Go](https://duckduckgo.com).")
                .moreText("Some more text")
                .sections(sections)
                .build();

        HeroImage heroImage  = HeroImage.builder()
                .image("data:image/gif;base64,R0lGODlhEAAQAMQAAORHHOVSKudfOulrSOp3WOyDZu6QdvCchPGolfO0o/XBs/fNwfjZ0frl3/zy7////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAABAALAAAAAAQABAAAAVVICSOZGlCQAosJ6mu7fiyZeKqNKToQGDsM8hBADgUXoGAiqhSvp5QAnQKGIgUhwFUYLCVDFCrKUE1lBavAViFIDlTImbKC5Gm2hB0SlBCBMQiB0UjIQA7\n")
                .text("Image Text binary data")
                .build();
        List<HeroImage> heroImages = new ArrayList<>();
        heroImages.add(heroImage);

        Button button1 = Button.builder()
                .type("openUrl")
                .title("Button 1")
                .text("Button 1T")
                .value("http://google.com")
                .build();
        Button button2 = Button.builder()
                .type("openUrl")
                .title("Button 2")
                .text("Button 2T")
                .value("http://google.com")
                .build();
        List<Button> buttons = new ArrayList<>();
        buttons.add(button1);
        buttons.add(button2);

        HeroCardContent heroCardContent = HeroCardContent.builder()
                .title("Ben's trying a hero image card")
                .subtitle("This is a subtitle")
                .text("And here we have some text")
                .images(heroImages)
                .buttons(buttons)
                .build();
        HeroCard hereoMessageCard = HeroCard.builder()
                .contentType("application/vnd.microsoft.card.hero")
                .content(heroCardContent)
                .text("Hero Card Text")
                .build();


        System.out.println("messageCard = " + messageCard);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String jsonString = gson.toJson(messageCard);
        System.out.println("jsonString = " + jsonString);
        SendToTeamsViaWebhook.sendJsonString(jsonString);
        System.out.println("Sent to Teams");

    }

    public static void createAndPostContext() {
        IMingleClient.Context context = new IMingleClient.Context();

        context.setWorksheetName("IPLS Inventory Scenario Days Cover Chart");
        context.setItemName("All Items");
        context.setBucketName("Weekly Calendar");
        context.setLocationName("All Warehouse");
        context.setCyclePeriodId(87L);
        context.setScenarioId(206L);
        context.setLogNoteId(22L);
        context.setTypeName("Balance Review Commentary");
        context.setSeverityName("Low");
        context.setUser("sopadmin");
        context.setModuleName("Demand");
        context.setParentWorksheetName("IPLS Inventory Scenario Days Cover Chart");
        context.setParentWorkSheetDisplayName("Days Cover Chart");

        context.setSummary("I am a post Message Summary");
        context.setDetails("I am some details");
        context.setImageUrl("https://cdn.discordapp.com/attachments/142923002055491585/910807514373816330/chart.png");
    }

    public void postMessage(final IMingleClient.Context context) {
        final String typeName = StringUtils.isEmpty(context.getTypeName()) ? null : context.getTypeName();
        final String summary = StringUtils.isEmpty(context.getSummary()) ? null : context.getSummary();
        final String details = StringUtils.isEmpty(context.getDetails()) ? null : context.getDetails();

        final byte[] imageBytes = context.getAttachmentContent();
        final String base64Image = context.getBase64Image();
        final String imageUrl = context.getImageUrl();
        List<Image> images = new ArrayList<Image>();

//		if (imageBytes != null) {
//			final String bytesString = Base64.getEncoder().encodeToString(imageBytes);
//			final Image bytesImage = Image.builder().image(bytesString).build();
//			images.add(bytesImage);
//		}
//
//		if ((base64Image != null) && (!base64Image.isEmpty())) {
//			final Image image = Image.builder().image(base64Image).build();
//			images.add(image);
//		}

        if ((imageUrl != null) && (!imageUrl.isEmpty())) {
            /**
             * HARD CODE HACK!!!
             */
            String hackImageUrl = "https://cdn.discordapp.com/attachments/142923002055491585/910807514373816330/chart.png";
            final Image image = Image.builder().image(hackImageUrl).build();
            images.add(image);
        }

//        List<DrillBack> drillbacks = createDrillbacks(context);
        List<PotentialAction> potentialActions = new ArrayList<>();
//
//        for (DrillBack db : drillbacks) {
//            //LOGGER.info("***BEDW*** db [" + db.getLabel() + "] [" + db.getUrl() + "]");
//            Target target = Target.builder().os("default").uri(db.getUrl()).build();
//            List<Target> targets = new ArrayList<>();
//            targets.add(target);
//            PotentialAction action = PotentialAction.builder().type("OpenUri").name(db.getLabel()).targets(targets).build();
//            potentialActions.add(action);
//        }

        Section heroSection = Section.builder().images(images).build();
        List<Section> sections = new ArrayList<Section>();
        sections.add(heroSection);
        String severityName = context.getSeverityName().toLowerCase().trim();
        Severity severity = Severity.valueOf(severityName);

        MessageCard messageCard = MessageCard.builder()
                .type("MessageCard")
                .context("http://schema.org/extensions")
                .title(typeName + " - " + summary)
                .themeColor(severity.getColourHexCode())
//				.text(details+"<br/> https://nlbavwscp1.infor.com:9094/#worksheet@Demand@IPLS+Forecast+Graph@Inventory+(M)") //TODO BEDW HACK HACK HACK Manually adding a url
                .text(details)
                .sections(sections)
                .potentialAction(potentialActions)
                .build();

        System.out.println("messageCard = " + messageCard);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String jsonString = gson.toJson(messageCard);
        LOGGER.info("***BEDW*** jsonString = " + jsonString);
        System.out.println(jsonString.length() + " chars");
        LOGGER.info("***BEDW*** " + jsonString.getBytes(StandardCharsets.UTF_8).length + " bytes");
        SendToTeamsViaWebhook.sendJsonString(jsonString);
        System.out.println("Sent to Teams");
    }

}
